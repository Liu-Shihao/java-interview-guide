# Redis
## Redis基本数据类型
### 1. String字符串
字符串是Redis最基本的数据类型，可以存储文本、整数或二进制数据，最大可存储512MB。
常用命令：
```shell
SET key value
GET key
INCR key
DECR key
INCRBY key increment
DECRBY key decrement
APPEND key value
SETNX key value
SETEX key seconds value
```
应用场景：
- 缓存：存储频繁访问的数据如网页内容、API响应等，减轻数据库压力
- 计数器：利用INCR/DECR实现文章浏览量、点赞数等统计功能
- 分布式锁：通过SETNX(SET if Not eXists)命令实现简单的分布式锁

### 2. Hash哈希
哈希类型是字段与值的映射表，特别适合存储对象。一个哈希可以存储多达2³²-1个字段-值对
常用命令：
```shell
HSET key field value
HGET key field
HGETALL key
HDEL key field
```
应用场景：
- 对象存储：存储用户信息、商品信息等，方便快速查询


### 3. List列表
列表是按插入顺序排序的字符串集合，支持从两端推入或弹出元素。列表的最大长度为2³²-1个元素
常用命令：
```shell
LPUSH key value
RPUSH key value
LPOP key
RPOP key
LRANGE key start stop
```
应用场景：
- 消息队列：：实现简单的消息队列系统，生产者用LPUSH添加消息，消费者用RPOP获取消息
- 最新列表：存储最新发布的文章、评论等，使用LRANGE获取最近N条记录

### 4. Set集合
集合是无序且唯一的字符串集合，提供高效的添加、删除和判断元素存在性操作。集合最大可存储2³²-1个成员
常用命令：
```shell
SADD key member
SREM key member
SMEMBERS key
```
应用场景：
- 唯一性集合​​：存储不重复的元素如用户ID集合、IP白名单等
- 社交网络​​：存储好友关系，使用SINTER计算共同好友、SADD实现添加好友等
- 标签系统：存储用户的标签，方便快速查询用户拥有哪些标签
- 抽奖系统：存储参与抽奖的用户ID，利用SADD实现去重，使用SRANDMEMBER命令实现随机抽取功能

### 5. Sorted Set / ZSet有序集合
有序集合类似于集合，但每个成员都关联一个分数(score)，用于排序。成员是唯一的(去重)，但分数可以重复。
常用命令：
```shell
ZADD key score member
ZRANGE key start stop [WITHSCORES]
ZREM key member
```
应用场景：
- 排行榜：存储用户的积分、排名等，根据分数排序实现用户排名、商品热度榜等。使用ZADD添加成员，ZRANGE获取排名前N的用户
- 优先队列：将优先级作为分数，实现优先级队列
- 时间线：使用时间戳作为分数，实现按时间排序的消息流
- 范围查询：查找分数在某一区间的成员，如查找价格在100-200元的商品

### Redis是单线程还是多线程？为什么这么快 高频！！
1. 基于内存操作：Redis将数据存储在内存中，而内存的读写速度远高于磁盘，使得Redis能够避免磁盘I/O的瓶颈，直接通过内存操作实现每秒数十万甚至百万级的操作。
2. 单线程模型：避免锁竞争和上下文切换
3. I/O多路复用：
   - 单线程监听多个连接：Redis采用单线程模型，通过一个线程管理多个客户端Socket连接，当某个连接有数据可读或可写时，才出发对应操作。
   - 非阻塞I/O,所有 Socket 设置为非阻塞模式，读写操作不会阻塞线程。。

多路复用器：Redis使用系统提供的多路复用器（如epoll）作为事件监听器，监听所有连接的可读或可写事件。
工作流程：
1. 将客户端 Socket 注册到多路复用器
2. 多路复用器阻塞等待（直到至少一个 Socket 就绪）
3. 当某个 Socket 可读/写时，多路复用器返回就绪的 Socket 列表
4. Redis 单线程依次处理这些就绪的 Socket 请求

Redis的I/O多路复用模型与Java NIO相似：
1. 非阻塞IO：两者都基于非阻塞 Socket，避免线程被单个连接阻塞。
2. 多路复用器：Java NIO 的 Selector 和 Redis 的 epoll 作用类似，都是通过一个中央组件监听多个连接事件。

什么是epoll？
`epoll`是 Linux 内核提供的高效 `I/O 多路复用机制`，相比传统的 `select/poll` 有显著优化。
**事件驱动：仅返回就绪的 Socket，无需遍历所有连接（select/poll 需要轮询全部 Socket）。**


### Redis的持久化机制 RDB和AOF
Redis持久化是指将内存中的数据保存到磁盘上，以防止进程退出或系统崩溃导致数据丢失的机制。Redis提供了两种主要的持久化方式：
1. RDB（Redis Database）：通过创建​`​内存快照`​​的方式，将某个时间点的数据集保存到磁盘上的**二进制文件**中。
   **过程**：Redis主进程​​fork一个子进程​​专门负责持久化操作，子进程将内存数据写入​​临时RDB文件。写入完成后，用临时文件​​替换旧RDB文件​。子进程退出，主进程继续服务客户端请求。
   **优点**：恢复速度快，直接加载二进制文件，文件大小小。
   **缺点**：可能丢失最后一次快照后的数据。


2. AOF（Append-Only File）：通过​`​记录写操作命令​`​的方式，将所有修改数据的命令追加到日志文件（默认appendonly.aof）中。期重写AOF文件以压缩体积
   **工作流程**：执行写命令并将命令传播到AOF缓冲区；根据appendfsync策略将缓冲区内容写入AOF文件；根据appendfsync策略将文件同步到磁盘；
   **优点**：数据安全性高，即使文件损坏也可以通过重放日志恢复数据。
   **缺点**：文件体积大，恢复速度慢。

### Redis有哪些集群？分片集群和主从集群的区别
Redis 集群主要有 三种核心模式

1. 主从复制模式： 
   - 单主多从：1个主节点（Master）处理写请求，多个从节点（Slave）复制主节点数据，提供读能力。
   - 数据同步：异步复制（默认）或半同步复制（需配置）。
   - 故障手动切换：主机节点宕机时。需要人工干预或者借助哨兵提升从节点为主节点。

2. 集群模式（Redis Cluster）
   - 数据分片（Sharding）：数据分散在16384个哈希槽（Slot）中，由多个主节点共同承担。
   - 多主多从：每个主节点对应1个或多个从节点，支持自动故障转移。
   - 去中心化：客户端可直接连接任意节点，节点间通过Gossip协议通信。

3. 哨兵模式（Sentinel）：哨兵模式是一种高可用的解决方案，用于监控和管理Redis主从复制集群。

### 缓存穿透和雪崩产生的条件以及解决方案

1. 缓存穿透（数据不存在）：用户不断请求缓存和数据库中都`不存在的数据`。例如请求ID为-1或特别大不存在的数据，每次请求都绕过缓存直接访问数据库，导致数据库压力过大。
    解决方案：
       - 布隆过滤器 Bloom Filter：将所有可能存在的数据哈希到一个足够大的bitmaps中，一个一定不存在的数据会被这个bitmaps拦截掉，从而避免了对不存在的数据进行查询。

2. 缓存击穿（单个数据过期）：**某个**热点key在失效的瞬间，大量并发请求同时访问这个key，导致所有请求都落到数据库上，造成数据库瞬时压力过大。
    解决方案：
       - 设置热点数据永不过期：对极热点数据不设置过期时间
       - 使用分布式锁：当数据过期时，使用分布式锁来保证只有一个线程去数据库查询数据并回填缓存。

3. 缓存雪崩（大量数据过期）：**大量**key在同一时间失效，导致大量请求直接访问数据库，造成数据库压力过大。
    解决方案：
       - 随机过期时间：为每个key设置不同的过期时间，避免同一时间大量key失效。
       - 热点数据永不过期：对极热点数据不设置过期时间。
       - 缓存预热：在系统启动时，将热点数据提前加载到缓存中。


### 布隆过滤器 Bloom Filter
布隆过滤器（Bloom Filter）是一种高效的概率型数据结构，用于快速判断一个元素是否可能存在于一个集合中。
空间效率高，查询速度快。
存在误判率：可会误判不存在的元素存在；但是不会误判存在的元素为不存在。

核心原理是使用 位数组：布隆过滤器使用一个长度为 m 的二进制位数组（初始全为 0）存储数据。
使用k个不同的哈希函数，每个函数将输入元素映射到位数组的某个位置。
对元素进行k次哈希，得到k个位置，将这些位置这是为1

查询元素时，对元素进行k次哈希，检查对应的k个位置是否都为1.
如果全部为1，可能存在；（可能误判）
如果有一个为0，一定不存在。
即如果不存在就是不存在！无漏判。




### 如何保证缓存和数据库数据一致性？

- 读操作：先查缓存，缓存未命中再查数据库
- 写操作：先更新数据库，再删除缓存。

并发读写时可能导致脏读（如线程A未完成数据库更新时，线程B读到旧值并回填缓存）

可以通过 延迟双删+ MQ重试 方案。

1. 更新数据库
2. 删除缓存
3. 延迟一段时间（比如500ms）
4. 再次删除缓存

延迟二次删除的核心是：为了防止旧数据重新回填缓存。
在第一次删除缓存后，若数据库更新未完成，其他并发请求可能从数据库读取到​​旧数据​​并重新写入缓存。
第二次删除（延迟后）可清理这段时间内可能被错误回填的旧数据，确保后续请求重新加载最新数据。
若数据库采用主从架构，从库同步可能存在延迟。延迟时间需覆盖主从同步耗时，避免从库未更新时读请求将旧数据写入缓存
加上MQ消息队列 重试机制：将删除缓存操作发送到MQ，失败时重试直至成功


## Redis缓存淘汰策略
Redis 的淘汰策略（Eviction Policy）用于在内存不足时决定哪些键（key）会被删除以释放空间。
1. 不淘汰策略（noeviction）：当内存不足以容纳新写入数据时，新写入操作会报错。（默认策略）
2. 随机淘汰策略（random）：随机选择键进行删除。
   - 随机删除一个设置了过期时间的键。
   - 随机删除任意键（包括未设置过期时间的键）。
3. 所有键LRU（Least Recently Used）最近最少使用：
   - 从设置了过期时间的键中，淘汰最近最少使用（Least Recently Used）的键。
   - 从所有键中淘汰最近最少使用的键。
4. 所有键LFU（Least Frequently Used）最不经常使用：
   - 从设置了过期时间的键中，淘汰访问频率最低（Least Frequently Used）的键。
   - 从所有键中淘汰访问频率最低的键。



## Redis分布式锁

