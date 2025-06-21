## 面试题
- Spring的Bean是单例的吗？线程安全吗？
- 为什么要用SpringBoot？SpringBoot与SpringCloud的区别？
- 说一下SpringBoot常用的注解？
- Spring APO在什么场景下会失效？
- Spring是如何解决循环依赖的问题？
- SpringBoot中自动装配机制的原理？
- 分布式事务的原理？

# Spring

## Spring的Bean是单例的吗？线程安全吗？
Spring Bean默认是单例的。
单例Bean的线程安全取决于其状态（State）的设计：
- 无状态Bean（Stateless）：线程安全，如果Bean不包含可变的成员变量（即无状态），则多线程并发访问时不会存在资源竞争。
- 有状态Bean（Stateful）：非线程安全，需通过同步机制（如synchronized、ThreadLocal、并发类等）保证线程安全。

## Spring Bean的生命周期


Bean的创建阶段：
1. 实例化Bean：通过反射机制调用Bean的构造函数创建Bean实例。
2. 属性注入：Spring通过依赖注入（DI）为Bean的属性赋值（如@Autowired、@Value等）。处理setter注入或字段注入。
3. Aware接口回调（可选实现）：如果Bean实现了Spring的Aware接口（如ApplicationContextAware、BeanNameAware等），则会回调这些接口方法（setBeanName()/setBeanFactory()）。

4. BeanPostProcessor前置处理（postProcessBeforeInitialization）：所有BeanPostProcessor的`postProcessBeforeInitialization()`方法会被调用。

5. `@PostConstruct` 注解方法 :依赖注入完成之后，初始化方法执行之前。

6. InitializingBean & init-method（初始化逻辑）：如果Bean实现了InitializingBean接口，Spring会调用afterPropertiesSet()。如果配置了`init-method`（如@Bean(initMethod = "init")或XML中的init-method），Spring会调用该方法。
7. BeanPostProcessor后置处理（postProcessAfterInitialization）：所有BeanPostProcessor的p`ostProcessAfterInitialization()`方法会被调用。

8. Bean完全初始化后，存入Spring容器（ApplicationContext），供其他Bean或代码调用。

9. Bean销毁阶段: `@PreDestroy` 注解方法 : 当Bean被销毁时，会调用@PreDestroy注解的方法。在容器销毁前调用的方法，做资源清理、日志、关闭连接等。
10. DisposableBean & destroy-method（销毁逻辑）：如果Bean实现了DisposableBean接口，Spring会调用`destroy()`。如果配置了`destroy-method`（如@Bean(destroyMethod = "destroy")或XML中的destroy-method），Spring会调用该方法。



Spring 管理 Bean 的整个生命周期，从创建实例到填充属性、初始化、销毁。

# SpringBoot

## 自动配置原理

## 如何编写一个自定义Spring Boot Starter?

# SpringCloud

## 面试题

- SpringCloud常见组件有哪些？



# Redis

- 如何保证Redis和Mysql的数据一致性？
- Redis的持久化方式有哪些？
- Redis的过期策略有哪些？
- Redis的缓存淘汰策略有哪些？
- Redis的分布式锁如何实现？


## Redis分布式锁实现
分布式锁是一种在分布式系统环境下，多个节点对共享资源进行访问控制的同步机制。它的主要目的是保证同一时刻只有一个节点能够对共享资源进行操作，从而避免数据不一致性问题。
在单体应用中，我们可以使用Java的synchronized或ReentrantLock等机制控制线程并发。但在分布式系统中，由于服务部署在多台机器上，这些本地锁机制无法跨进程生效，这就需要分布式锁来实现跨JVM的互斥控制
Redis因其高性能和丰富的原子操作，成为实现分布式锁的常用选择。

### 方案一：SETNX + EXPIRE
SETNX是SET if Not eXists的缩写，它的作用是当键不存在时，设置键的值。
EXPIRE用于设置键的过期时间。
```java
if(jedis.setnx("lock_key", "lock_value") == 1) {
    jedis.expire("lock_key", 30); // 设置过期时间
    try {
        // 业务逻辑
    } finally {
        jedis.del("lock_key"); // 释放锁
    }
}
```
问题​​：SETNX和EXPIRE是两个独立操作，不具有原子性。如果在执行EXPIRE前客户端崩溃，会导致锁无法自动释放，形成死锁

Redis 2.6.12后，SET命令支持NX、EX等扩展参数，可以原子性地实现加锁和设置过期时间
```java
String result = jedis.set("lock_key", "lock_value", "NX", "EX", 30);
if ("OK".equals(result)) {
    try {
        // 业务逻辑
    } finally {
        jedis.del("lock_key");
    }
}
```
- NX参数：当键不存在时才设置值
- EX参数：设置键的过期时间（单位：秒）。例如EX 30表示键在30秒后自动删除


问题：锁被其他客户端误删：如果客户端A执行时间超过30秒，锁自动释放，客户端B获取锁后，客户端A完成操作时可能误删B的锁

### 方案二：SET + 唯一值验证
为避免误删，可以为每个客户端生成唯一标识作为锁值，释放时验证是否为自己的锁：
```java
String clientId = UUID.randomUUID().toString();
if ("OK".equals(jedis.set("lock_key", clientId, "NX", "EX", 30))) {
    try {
        // 业务逻辑
    } finally {
        // 使用Lua脚本保证原子性
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                       "return redis.call('del', KEYS[1]) " +
                       "else return 0 end";
        jedis.eval(script, Collections.singletonList("lock_key"), 
                  Collections.singletonList(clientId));
    }
}
```
通过唯一值验证避免了误删问题，使用Lua脚本保证验证和删除的原子性


### 方案三：Redisson
上述方案仍存在业务执行时间超过锁有效期的问题。Redisson提供了更完善的解决方案：
1. ​​看门狗机制​​：获取锁成功后，启动后台线程定期（默认每10秒）检查并延长锁有效期，只要客户端还存活就不断续期，避免业务未完成锁已过期
2. 可重入锁​​：通过计数器记录重入次数，同一线程可多次获取同一把锁。
3. 公平锁​​：基于Redis的List结构实现先到先服务的公平锁。
4. 联锁（MultiLock）​​：将多个锁合并为一个锁，所有锁都获取成功才算成功。

```java
RLock lock = redisson.getLock("myLock");
lock.lock(); // 默认30秒过期，看门狗自动续期
try {
    // 业务逻辑
} finally {
    lock.unlock();
}
```

## 缓存击穿、缓存穿透、缓存雪崩



## Zookeeper

ZooKeeper 是一个开源的分布式协调服务框架，它通过简单的接口和高效的一致性协议，为分布式系统提供配置管理、命名服务、集群协调等关键功能。

核心机制：
1. Watcher机制：客户端可以注册Watcher，当节点状态发生变化时，ZooKeeper会通知客户端。
2. 数据模型：ZooKeeper的数据模型类似于文件系统，每个节点称为ZNode，每个ZNode可以存储数据和子节点。
3. 一致性协议：ZooKeeper采用了ZAB（Zookeeper Atomic Broadcast）协议，分为选举、发现、同步、广播四个阶段，确保数据强一致性和崩溃恢复能力。
4. ACL权限控制：每个ZNode可以设置ACL权限，支持 CREATE、READ、WRITE 等五种权限，控制对节点的访问。
5. 分布式锁：ZooKeeper可以用于实现分布式锁，通过创建临时顺序节点实现。
6. 会话Session：客户端与服务端通过 TCP 长连接维持会话，超时或断开后临时节点自动清理


Zookeeper
优点：强一致性、高可用性、实时通知
缺点：写性能较低（需要集群共识），不适合存储大数据量。

### Zookeeper架构

- Leader
- Follower
- Observer

### Zookeeper数据结构（ZNode）
类似文件系统的树形结构，每个 `ZNode` 可存储不超过 1MB 的数据
节点类型：
- 持久节点（PERSISTENT）：创建后除非手动删除，否则不会消失
- 持久顺序节点（PERSISTENT_SEQUENTIAL）：类似持久节点，但会自动添加序号后缀
- 临时节点（EPHEMERAL）：创建后连接断开即删除
- 临时顺序节点（EPHEMERAL_SEQUENTIAL）：类似临时节点，但会自动添加序号后缀  


### Zookeeper实现分布式锁
Zookeeper作为分布式协调服务，其强一致性和临时节点特性非常适合实现分布式锁。
1. ​临时顺序节点（Ephemeral Sequential Node）​​：客户端创建的临时节点在会话结束后会自动删除，避免死锁；顺序特性保证节点编号有序。
2. Watcher机制​​：客户端可以监听节点的变化事件，实现通知机制。
3. ​强一致性​​：基于ZAB协议，所有节点的数据视图一致。

基本实现步骤：
1. ​​创建锁节点​​：客户端尝试创建`临时顺序节点`，如`/locks/lock_0000000001`。
2. ​​获取锁列表​​：客户端获取`/locks`下的所有子节点，并按节点编号排序。
3. ​​判断是否获得锁​​：如果当前节点编号最小，则获得锁；否则，监听前一个节点的删除事件。
4. ​​释放锁​​：当业务逻辑完成后，删除当前节点，释放锁。
5. 异常处理：客户端连接断开或网络异常时，临时节点会自动删除，释放锁。

注意：Zookeeper实现公平锁的关键在于​`​临时顺序节点`​​的使用:
ZooKeeper的临时顺序节点（EPHEMERAL_SEQUENTIAL）会为每个客户端创建的节点自动附加一个全局递增的序号（如lock-00000001、lock-00000002）。通过判断当前节点是否是最小编号节点，可以确保​​先到先服务​​的公平性，避免锁的饥饿问题



#### 使用Curator客户端实现Zookeeper分布式锁
Apache Curator 是Zookeeper的高级客户端，提供了分布式锁的直接实现：
```java
InterProcessMutex lock = new InterProcessMutex(client, "/locks/resource");
if (lock.acquire(10, TimeUnit.SECONDS)) { // 带超时的获取锁
    try {
        // 业务逻辑
    } finally {
        lock.release();
    }
}
```

Zookeeper分布式锁的优点：
- 强一致性​​：基于ZAB协议保证所有客户端看到的锁状态一致
- 自动释放：临时节点在会话结束后自动删除，避免死锁
- 公平性：节点顺序创建天然实现先到先服务的公平锁
- 无超时风险：不依赖时间机制，适合长时间任务


若系统要求​​高性能+最终一致性​​，选择Redis（配合Redisson）
若系统要求​​强一致性+高可靠​​，选择Zookeeper（配合Curator）






