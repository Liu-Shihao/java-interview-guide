# 面试情景演练


# 1. 你说你有JVM调优经验，你在项目里是如何做的？
我们可以通过分析一下几个关键指标来进行调优：
1. GC回收频率和停顿时间
2. 堆内存使用情况
3. 线程数、CPU使用率
4. 请求延迟和吞吐量


工具选择：
JDK自带的 `jstat`、`jmap`、`jstack`等工具
```shell
# 基础工具
jstat -gcutil <pid> 1000  # 每1秒打印GC情况
jmap -histo <pid>         # 对象分布统计
jstack <pid>              # 线程快照
```

高级工具：
1. Arthas 阿里开源的诊断工具
1. jvisualvm/jprofiler   图形化分析工具
3. GCViewer GC日志分析工具



| 工具                                  | 作用         |
| ----------------------------------- | ---------- |
| `jps` / `jstack`                    | 查看进程、线程堆栈  |
| `jmap`                              | 堆转储、对象分布   |
| `jstat`                             | 监控GC、类加载   |
| `VisualVM` / `Java Mission Control` | 图形化监控工具    |
| `async-profiler` / `perf`           | CPU/内存性能分析 |



配置参数获取GC日志：

```bash
-XX:+PrintGCDetails -XX:+PrintGCDateStamps 
-XX:+PrintGCTimeStamps -Xloggc:/path/to/gc.log
```

堆转储配置：
```bash
-XX:+HeapDumpOnOutOfMemoryError 
-XX:HeapDumpPath=/path/to/dump.hprof
```

### 合理设置堆内存大小
通过 -Xms（初始堆大小） 和 -Xmx（最大堆大小）控制内存：
```bash
-Xms2g -Xmx2g
-Xms4g -Xmx4g
-Xms16g -Xmx16g
```
初始值尽量与最大值一致，避免频繁的堆扩展。

### 选择合适的垃圾收集器

根据应用类型选择合适的GC算法：
| GC类型                        | 特点          | 适用场景          |
| --------------------------- | ----------- | ------------- |
| SerialGC                    | 单线程GC，低内存开销 | 小型应用          |
| ParallelGC                  | 多线程GC，高吞吐   | 高并发、批处理       |
| CMS (Concurrent Mark Sweep) | 响应快，停顿小     | 响应时间敏感型应用     |
| G1 GC                       | 分区收集、低延迟    | 大型服务，内存 > 4GB |
| ZGC / Shenandoah (JDK11+)   | 低延迟GC       | 极低延迟要求场景      |


```
-XX:+UseG1GC 
-XX:+UseZGC    
```

### 应用层优化
1. 避免内存泄露：注意缓存使用（如ThreadLocal、静态集合等），定义review长生命周期对象。
2. 减少对象创建：服用对象（如StringBuilder），使用对象池（如线程池、连接池）。
3. 优化IO操作（使用NIO或异步IO），减少阻塞操作，合适设置缓冲区
4. 合理配置线程池
5. 使用高效的数据结构：如ConcurrentHashMap、linkedBlockQueue减少锁竞争





### 短声明周期对象堆，Minor GC频繁

优化方案：
- -Xms4g -Xmx4g  扩大堆内存大小，初始堆大小和最大堆大小一致，避免频繁扩容
- -xmn=2g，调整新生代大小，增大新生代空间，减少Minor GC次数
- -XX:SurvivorRatio=8 调整Eden与Survivor比例，减少Survivor空间占用，提高对象在新生代的存活时间
- 
```bash
# 推荐参数（JDK8+）
-Xms4g -Xmx4g               # 堆大小固定避免扩容
-Xmn2g                      # 新生代占1/2堆
-XX:SurvivorRatio=8         # Eden与Survivor比例
-XX:+UseG1GC                # G1收集器
-XX:MaxGCPauseMillis=200    # 目标暂停时间
-XX:InitiatingHeapOccupancyPercent=45  # 老年代占用触发阈值
```

### 存在大对象，Major GC频繁

- 调大堆内存大小，减少新生代占比，手动开启ZGC垃圾收集器
```bash
# 推荐参数
-Xmx16g -Xms16g
-XX:NewRatio=3              # 新生代占1/4堆
-XX:+UseConcMarkSweepGC     # CMS收集器（JDK8）
-XX:+UseZGC                 # ZGC（JDK15+）
-XX:ConcGCThreads=4         # 并发GC线程数
-XX:ParallelGCThreads=8     # 并行GC线程数
```

### 低延迟系统优化（金融交易类）
典型特征：要求GC停顿时间极短
可以手动开启ZGC垃圾收集器，降低停顿时间
```bash
# 推荐参数（JDK11+）
-Xmx8g -Xms8g
-XX:+UseZGC                 # 或ShenandoahGC
-XX:SoftRefLRUPolicyMSPerMB=0  # 禁用软引用延迟清理
-XX:ZAllocationSpikeTolerance=5 # 分配尖峰容忍度
-Djava.util.concurrent.ForkJoinPool.common.parallelism=4 # 控制并行度
```





### Full GC频繁
排查步骤：
1. 是否显示调用了System.gc()
2. 分析来年代的内存占用趋势
3. 检查元空间是否不足


## 优化效果验证
|指标|优化前|优化后|
|---|---|---|
|Full GC频率|2次/小时|<1次/天|
|Young GC平均耗时|50ms|<20ms|
|最大停顿时间|1.2s|<200ms|
|吞吐量下降比例|15% during GC|<5% during GC|


## 实践建议
1. 性能基准测试：见后对比调整结果
2. 细化分代大小：例如`-XX:NewRatio=2 -XX:SurvivorRatio=6`,新生代与老年代比例，Eden和Survivor比例
3. 定义进行GC日志分析：发现内存泄露或频繁Full GC问题
4. 自动化监控告警：结合prometheus、grafana等工具



# 如何保证跨服务转账的一致性？

转账服务涉及A服务扣款和B服务加款，需要保证原子性

避免分布式事务引入性能瓶颈，同时保证最终一致性
>Saga模式​​ 是一种用于管理 ​​分布式事务​​ 的设计模式，核心思想是将一个长事务拆分为多个本地事务，通过 ​​补偿机制​​ 保证最终一致性。它适用于微服务架构中跨服务的业务场景（如电商下单、银行转账等），避免传统2PC（两阶段提交）的性能瓶颈和单点问题。

采用Saga模式，将大事务拆解为本地事务+补偿机制：
1. A扣款 -> 发MQ -> B加款;
2. 补偿流程： 如果B加款失败，通过MQ触发A退款

幂等性：为每条转账请求生成唯一ID，通过t-transaction_log去重

# 多轮对话设计

多轮对话(Multi-turn Conversation)是指用户与LLM Agent之间进行的连续、有上下文关联的多次交互。

1. 短期记忆：通过内存缓存或者Redis缓存存储单轮对话的上下文信息。
2. 长期记录：将用户的历史对话信息存储到Oracle数据库中。

# 用户可能在对话中切换意图，如何设计？
如从”查询余额“突然改为”转账“，如何**平衡上下文的连贯性和无效历史干扰**？

1. Prompt设计：明确指令，如果用户当前消息的意图与历史消息无关，则忽略历史消息。


# 生产环境转账服务突然变慢，如何排查？

1. 第三方API相应是否超时
2. 数据库连接是否异常？SQL慢查询
3. 缓存是否异常？
4. MQ消息



# 未来3年的职业规划

未来还是以技术为主，提高自己技术开发的能力以及系统架构设计的经验。
同时目前LLM大模型技术发展比较快，打算继续学习了解LLM大模型相关的内容和知识。


