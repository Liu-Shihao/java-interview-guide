# Message Queue消息队列


- 如何处理消息队列的消息挤压问题？
- Kafka单分区单消费者实例，如何提高吞吐量？

## 如何保证消息不丢失？

1. 在消息发送阶段
   - 生产者确认机制：使用消息确认机制（ACK），确保消息成功到达消息队列，同步等待Broker的确认响应。
2. 消息存储阶段：将消息持久化到磁盘上，而非仅内存，写入磁盘后再返回ACK
3. 消息消费阶段
   - 消费者确认机制：消费者在正确处理完消息后，手动发送ACK确认消息。
   - 消费者幂等性：确保消费者能够处理重复消息，避免重复消费。

以Kafka为例：
生产者端设置 `acks=all`或者`acks=-1`，等待所有ISR副本确认（最高可靠性）
Broker端保证：
- 副本机制：每个分区有多个副本（replication.factor配置）
- 持久化：消息直接写入磁盘

消费者端保证：`enable.auto.commit=false`关闭自动提交，处理完消息后手动提交offset


## 如何保证消息不重复消费（消息消费的幂等性）
1. 消费端幂等性设计：无论消费多少次，结果都一样，不影响业务状态。使用数据库的唯一约束，插入数据时先判断记录是否存在。重复插入会抛出异常。

2. 使用消息ID去重：消息中带有唯一ID（如UUID、订单号、traceId），消费前判断该消息ID是否已处理，处理过就丢弃。

3. MQ消费确认机制（ACK）：确保消费完后再 ack
   - RabbitMQ: 使用手动 ack，确保业务成功后才 ack。`channel.basicAck(deliveryTag, false);`
   - Kafka: 使用 offset 提交机制，消费成功后再提交 offset。配置 `enable.auto.commit=false`，手动提交 offset。
- Kafka可以设置幂等生产者设置`enable.idempotence=true`，消费者端确保offset提交与业务处理的原子性。

4. 事务一致性控制：将消费逻辑和确认ack或offset提交绑定在一个事务中，避免“消费成功但没提交”的问题。

## 如何保证消息的顺序性？

1. 按消息的 Key 分区（Partition）发送（适用于：Kafka、RocketMQ 等分区模型的 MQ）
在kafka中，partition分区内部的消息是有序的，但是多个Partition之间是无序的。按消息的 Key 分区（Partition）发送：将具有相同业务标识（如订单ID）的消息发送到同一个分区（Partition）。**消费端针对每个分区单线程消费或按照消息顺序依次处理。**
2. 一个 Topic 对应一个消息流（适用于：不支持分区模型但支持 Topic 隔离的 MQ，如 RabbitMQ（使用多个队列））
对于需要顺序的消息，为每一类业务Key设置一个单独的 Queue/Topic。消费端是一对一消费队列，顺序可控。


多个消费者是否能保证消息顺序消费？
在kafka设计中，一个 Topic 的 Partition 会被同一个消费者组内的消费者分配唯一拥有权：
- 一个 Partition 同时只能被一个消费者消费（不允许多个消费者并发消费同一 Partition）
- 不同 Partition 可以被不同消费者并行消费



# Kafka
Kakfa核心组件：
| 组件                     | 说明                                                  |
| ---------------------- | --------------------------------------------------- |
| **Producer**           | 生产者，发送消息到 Kafka Topic                               |
| **Consumer**           | 消费者，从 Topic 中拉取消息                                   |
| **Broker**             | Kafka 服务器节点，存储并转发消息                                 |
| **Topic**              | 逻辑上的消息分类容器，可以有多个分区（Partition）                       |
| **Partition**          | 一个 Topic 的物理分区，消息在分区内有序存储                           |
| **Zookeeper**（或 KRaft） | Kafka 的协调组件，用于选举、配置管理（Kafka 3.x 开始支持无 Zookeeper 架构） |

```less
[Producer] --> [Kafka Broker (Topic -> Partition)] --> [Consumer Group]
                             |
                          [Zookeeper]（或 KRaft）

```

kafka特点：
1. 消息持久化到磁盘（顺序写，性能高）
2. 消息消费是Pull 模式
3. 分区 + 消费组 提供了良好的可扩展性和顺序控制
4. 高吞吐量、适合大数据、日志、流式处理


Kafka中，每个Topic可以划分为多个Partition，每个Partition是一个有序的队列。
- 每个 Partition 内部的消息是 有序的
- 不同 Partition 之间消息是 无序的

```sql
Topic: "order-events"
 ├── Partition 0
 ├── Partition 1
 └── Partition 2

```

消费特性：
- Partition 只能被 Consumer Group 中的一个 Consumer 消费（保证每条消息只被处理一次）
- 一个 Consumer 可以消费多个 Partition
- Kafka的消息消费是Pull模式，Consumer需要主动向Broker请求消息。

### Kafka Partition 的顺序性如何保证？
- Partition 内：消息是严格按照写入顺序追加的 → 有序
- 多个 Partition：之间无顺序保证 → 全局无序

如果需要顺序消费，要确保：
1. 同一个 Key 的消息落入同一个 Partition（比如 orderId）
2. 消费端对该 Partition 使用单线程或顺序处理


示意图：
kafka分区生产与消费

```lua
+-----------+       +-------------------+       +--------------+
| Producer  |-----> |   Kafka Topic     |-----> |  Consumer    |
+-----------+       |   (3 Partitions)  |       |  Group       |
                    |                   |       |              |
                    |  P0   P1   P2     |       |   C0   C1    |
                    +-------------------+       +--------------+

```
1. Producer 将 Key 为 "user-123" 的消息写入 Partition 1
2. Consumer Group 中的 C1 被分配到了 Partition 1

---
### 总结：Kafka Partition 的关键点

| 项目    | 描述                               |
| ----- | -------------------------------- |
| 是什么   | Kafka 中 Topic 的物理分区，消息有序存储单元     |
| 为何重要  | 支持扩展、高并发、高可用、顺序性保障               |
| 如何选择  | 使用 Key Hash 分区或自定义 Partitioner   |
| 消费顺序  | 单个 Partition 内有序，多个 Partition 无序 |
| 与性能关系 | Partition 越多，可并行度越高，吞吐量越大        |


### Kafka 消息结构与存储
每条消息实际是写入磁盘的 顺序日志文件：
```pgsql
Partition 0:
    offset=0: {"key": "A", "value": "msg1"}
    offset=1: {"key": "A", "value": "msg2"}
    offset=2: {"key": "B", "value": "msg3"}

```
`Offset` **是 Kafka 自动生成的编号，代表消息在分区中的位置**
消费者通过 Offset 来追踪进度

# RabbitMQ
RabbitMQ 基于 AMQP 协议，关注可靠投递、灵活路由和协议完整性，适用于业务解耦、任务队列、微服务通信等。

RabbitMQ核心组件：
| 组件               | 说明                        |
| ---------------- | ------------------------- |
| **Producer**     | 发送消息                      |
| **Exchange**     | 消息路由器，根据规则将消息分发到队列        |
| **Queue**        | 存储消息的缓冲区，消费者从中读取          |
| **Consumer**     | 监听队列并消费消息                 |
| **Broker**       | RabbitMQ 服务本体             |
| **Binding**      | 连接 Exchange 与 Queue 的路由规则 |
| **Virtual Host** | 多租户隔离环境                   |

Exchange 类型：
- Direct Exchange：根据消息的 routing key 进行路由
- Topic Exchange：根据消息的 routing key 和 pattern 进行路由
- Fanout Exchange：将消息广播到所有绑定的队列
- Headers Exchange：根据消息的 headers 进行路由

架构图示：
```less
[Producer]
     |
 [Exchange] --- Binding --> [Queue] --> [Consumer]

```

RabbitMQ特点：
- Push 模式消费（可配置为 Pull）
- 消息可持久化、确认（ack）、事务、延迟等
- 灵活的路由机制，支持复杂业务逻辑
- 支持插件、管理界面丰富

---

| 特性       | Kafka          | RabbitMQ          |
| -------- | -------------- | ----------------- |
| 架构模型     | 分布式日志系统        | 基于 AMQP 协议的消息代理   |
| 消息传输模式   | Pull           | Push（支持 Pull）     |
| 消息存储     | 磁盘持久化（默认）      | 内存为主，可持久化         |
| 消息顺序性    | 分区内顺序          | 队列内顺序（无分区）        |
| 吞吐量      | 非常高（百万级）       | 中等偏高（万级）          |
| 消息是否自动删除 | 不一定（按保留策略）     | 消费后可自动删除          |
| 消费者状态管理  | 客户端控制 offset   | Broker 管理 ack 状态  |
| 适合场景     | 日志采集、流处理、大数据系统 | 微服务通信、业务异步解耦、任务调度 |
