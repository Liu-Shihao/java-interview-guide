# JD 618 面试准备

## Java基础


### 抽象类和接口的区别？


## 集合

### ArrayList和LinkedList的区别？
ArrayList和LinkedList都是List接口的实现类。
ArrayList是基于数组实现的，优点是查询效率高，缺点是插入和删除元素需要移动元素，效率较低。
LinkedList是基于链表实现的。改变空间结构容易，所以插入和删除元素效率较高，但是查询效率较低。


### HashMap的底层原理，扩容机制？
HashMap底层是数组 + 链表  和红黑树实现的






### 多线程情况下，一部分线程put，一部分线程get，如何保证线程安全？
从HashMap扩容过程和链表树化两个角度，扩容时会发生什么？树化会导致什么问题？get的线程会拿到什么值？



### ConcurrentHashMap的底层原理，什么情况下会产生锁全表？

### ThreadLocal的底层原理，如何保证线程安全？


### 集合里有若干元素，每个元素都包含一个开始时间和结束时间，怎么找到时间有重叠的元素？



如果元素数量非常大，没法放在一台机器上，怎么处理？用什么数据结构解决这个问题？




## 多线程


### 线程池有什么作用？
线程池可以避免频繁的创建和销毁线程，提高效率
### 如何创建线程池？

### 线程池的七大参数
- corePoolSize：核心线程数
- maximumPoolSize：最大线程数
- keepAliveTime：线程空闲时间
- unit：空闲时间单位
- workQueue：任务队列
- threadFactory：线程工厂
- handler：拒绝策略

### 线程池的执行流程

### 线程池的拒绝策略

### 线程池的工作队列

## JVM

### JVM内存模型

### JVM里的线程（GC线程）和我们自己创建的线程有什么不一样？


### 有没有遇到过内存问题？怎么排查的？


### 什么时候用CMS？什么时候用G1？ZGC呢？



## Spring
Spring的核心是`IOC`(Inversion of Control)**控制反转** 和`AOP`(Aspect-Oriebted Programming)面向切面编程。
### 什么是AOP？
AOP(Aspect-Oriebted Programming)是面向切面编程，是一种编程范式，是对OOP的补充。
它能够将一些与业务无关的代码从业务代码中分离出来，从而提高代码的复用性和可维护性。

核心概念：
- Aspect 切面，一个关注点的模块化，这个关注点可能会横切多个对象。
- JoinPoint 连接点：程序执行过程中的某一行为，比如方法的调用或者异常的抛出。
- Advice 通知：切面在某个特定的连接点上执行的动作。
- Pointcut 切入点：切面所通知的连接点。
- Weaving 织入：将切面应用到目标对象从而创建一个新的代理对象的过程。
有5种通知类型：
- Before Advice 前置通知：在方法执行之前执行
- After Advice 后置通知：在方法执行之后执行
- Around Advice 环绕通知：在方法执行前后都执行
- After Returning Advice 后置返回通知：在方法执行之后返回结果之后执行
- After Throwing Advice 后置异常通知：在方法执行之后抛出异常之后执行

AOP的优点：
- 代码解耦，将横切关注点与业务逻辑分离
- 提高代码的复用性
- 灵活性

AOP适用场景：日志记录、事务管理、权限控制、异常处理等

OOP(Object-Oriented Programming)是面向对象编程，是一种编程范式，是对数据的抽象。
它使用`对象`（Object，对象是类的实例，是一个具体的实体）和`类`（Class 类是对象的抽象，是一组对象的共同特征。）来组织代码和数据。
三大特点：
- 封装：将数据和操作数据的方法封装在对象中
- 继承：子类可以继承父类的属性和方法
- 多态：同一个方法可以有不同的实现

AOP是对OOP的补充，AOP通常需要建立在OOP基础之上。

### 什么是IOC？实现原理？
`IOC`(Inversion of Control)**控制反转** 是面向对象编程中的一种设计原则，也是Spring框架的核心思想之一，用于降低代码之间的耦合度。
**将创建对象的将控制权交给容器来管理。**

控制反转的含义:
- 传统控制流程：由应用程序主动创建和管理依赖对象
- IOC控制流程：将对象的创建和依赖管理的控制权交给容器（框架），应用程序只需要声明依赖关系，容器负责创建和管理对象。

控制反转意味着将原本由程序代码直接操控的对象创建和依赖管理权交给外部容器（如Spring容器）来管理。


`DI`(Dependency Injection)依赖注入是`IOC`的一种实现方式，它通过一下三种方式实现：
- 构造器注入
- Setter方法注入
- 字段注入：直接在字段上使用@Autowired注解，最常见的方式
- 方法注入


### Spring的Bean是单例的，线程安全吗？

### @Autowired注解与@Resource注解的区别？


### Spring Bean的生命周期
Spring Bean的生命周期是指从Bean的创建到销毁的过程

1. **实例化（Instantiation）**：容器通过构造函数或工厂方法创建 Bean 实例
2. **属性赋值（Populate）**：Spring通过反射或Setter方法为Bean的属性注入值
3. **Aware接口回调**：如果Bean实现了各种Aware接口，会调用相应的回调方法
   - BeanNameAware：获取Bean的名称
   - BeanFactoryAware：获取BeanFactory引用
   - ApplicationContextAware：获取ApplicationContext引用，可以访问其他Bean
4. **BeanPostProcessor前置处理器**：如果Bean实现了**BeanPostProcessor**接口，会调用前置处理器的方法 **postProcessBeforeInitialization()**。
5. **初始化（Initialization）**：Bean的初始化方法:
   - **@PostConstruct** 注解方法
   - 实现 **InitializingBean** 接口的 **afterPropertiesSet()** 方法
   - XML 中指定的 **init-method** 或 Java 配置中的 @Bean(initMethod = "...")
6. **BeanPostProcessor后置处理器**：如果Bean实现了BeanPostProcessor接口，会调用后置处理器的方法**postProcessAfterInitialization()**方法
7. 销毁（Destruction）：Bean的销毁方法：
   - **@PreDestroy** 注解方法
   - 实现 **DisposableBean** 接口的 **destroy()** 方法
   - XML 中指定的 **destroy-method** 或 Java 配置中的 ***@Bean(destroyMethod = "...")***

### Aware接口的作用

它们的主要作用是`让 Bean 能够感知到 Spring 容器的一些内部对象和运行环境`。这些接口为 Bean 提供了与 Spring 框架基础设施交互的能力。
- BeanNameAware：获取Bean的名称
- BeanFactoryAware：获取BeanFactory引用
- ApplicationContextAware：获取ApplicationContext引用，可以访问其他Bean
- EnvironmentAware：获取Environment引用
- ResourceLoaderAware：获取ResourceLoader引用
- MessageSourceAware：获取MessageSource引用
- ApplicationEventPublisherAware：获取ApplicationEventPublisher引用

### Spring如何解决循环依赖？

Spring 通过三级缓存解决循环依赖问题
什么是循环依赖？
当两个或者多个Bean互相依赖时形成循环：Bean A 内部依赖的 Bean B属性，而 Bean B 内部有依赖的 Bean A属性。

三级缓存结构：
- singletonObjects （成品对象）：单例池，存放已经实例化的单例Bean
- earlySingletonObjects （半成品对象）：提前暴露的单例池，存放已经实例化但还未完成属性注入的单例Bean
- singletonFactories：对象工厂，存放单例Bean的工厂方法，用于生成代理对象


**早期引用**：早期引用是指 Spring 在 Bean 完成完全初始化之前（即还在属性填充和初始化过程中）就提前暴露出来的对象引用。这种机制是 Spring 解决循环依赖的核心技术。
特点：
- 不完整状态：对象已实例化但未完成依赖注入和初始化回调
- 可被引用：其他 Bean 可以提前持有该引用
- 可能是代理对象：如果需要 AOP 代理，早期引用可能是代理对象而非原始对象

如果是普通 Bean：早期引用就是最终引用
如果是 AOP 代理：早期引用可能是代理对象而非原始对象


解决流程：
1. 创建A，调用构造器实例化A，将A的ObjectFactor放入三级缓存
2. 注入A的属性，发现需要注入B，于是暂停A的初始化，开始创建B
3. 创建B，调用构造器实例化B，同样将 B的ObjectFactor放入三级缓存
4. 注入B的属性，发现需要注入A
    - 从三级缓存获取A的ObjectFactor，执行 **getEarlyBeanReference()** 获取**早期引用**
    - 将A的早期引用放到二级缓存，清空A的三级缓存的ObjectFactor
    - 将A的早期引用注入到B中
5. 完成B的初始化，将B的实例化对象放入一级缓存
6. 返回A的初始化，将B的完整实例注入到A中，将A的实例化对象放入一级缓存



注意，当从三级缓存获取ObjectFactory并生成早期引用之后，需要把ObjectFactory从三级缓存中移除，**保证每个 Bean 的 ObjectFactory 只被调用一次，避免内存泄漏，维护缓存一致性**。


### Spring cglib动态代理和jdk动态代理的区别？
动态代理是 Spring AOP和许多框架实现的核心技术，主要分为JDK动态代理和CGLIB动态代理两种实现方式。

1. **JDK动态代理**：基于`接口`的动态代理，通过**反射机制**生成实现指定接口的代理类。核心类是`java.lang.reflect.Proxy`。运行时动态生成字节码。
**原理**：客户端调用代理类实例的方法，代理类将调用转发给InvocationHandler的invoke()方法，通过反射机制最终调用目标对象(原生类)的方法。
**优点**：JDK原生支持，不需要额外依赖
**限制**：只能代理接口，性能略低于CGLIB（方法调用时的反射开销）


2. **CGLIB动态代理**：基于`类`的动态代理，通过继承目标类生成子类。核心类是`net.sf.cglib.proxy.Enhancer`。使用**ASM框架**直接操作字节码。
优点：可以代理类和接口，性能优于JDK动态代理
限制：无法代理 final 类和方法，需要额外引入CGLIB依赖


Spring AOP根据目标对象的特性自动选择代理方式：
1. 目标实现了接口：默认使用JDK动态代理
2. 目标未实现接口：使用CGLIB代理
3. 强制使用CGLIB：通过@EnableAspectJAutoProxy(proxyTargetClass=true)配置


### Spring MVC的执行流程
`Spring MVC` 的核心流程围绕 ​`​前端控制器`（`DispatcherServlet`）​​ 展开，将请求分发给对应的处理器（`Controller`），并最终`渲染视图`返回给客户端。

1. **首先请求到达 `DispatcherServlet` 前端控制器**：所有的HTTP请求首先有DispatcherServlet 接收，它是Spring MVC的核心调度器。
2. **调用 `HandlerMapping` 处理器映射器**：DispatcherServlet 会调用 HandlerMapping 处理器映射器，根据请求的URL找到对应的处理器（`Controller`）。
3. **调用 `HandlerAdapter` 处理器适配器**：DispatcherServlet 通过 HandlerAdapter（处理器适配器）调用具体的Controller方法。
4. **执行 `Controller` 方法**：HandlerAdapter 使用 ArgumentResolver（参数解析器）解析方法参数（如 @RequestParam、@RequestBody 等）。返回结果（ModelAndView）
5. **处理返回值**：
   -  `ViewResolver 视图解析器` 解析视图（将逻辑视图名转换为实际视图），渲染视图（使用模型数据渲染视图）：使用模型（Model）渲染视图（View），将Model中的数据填充到视图中；
   - 或者直接响应数据，使用 `HttpMessageConverter` 将返回值转换为JSON/XML等格式，直接写入响应流。
6. **返回响应给客户端**：DispatcherServlet 将处理结果返回给客户端。




## Spring Cloud 分布式微服务
**Spring Cloud 提供了微服务架构的完整解决方案**
### SpringCloud组件
1. 服务注册与发现：维护服务实例的动态列表。通过心跳机制监控实例状态，自动移除不可用实例。
   - Eureka：服务注册中心，支持服务实例的动态注册于发现。
   - Nacos：集服务注册、配置中心于一体，支持动态服务列表和健康检查。提供了有好的管理页面

服务调用不直接通过注册中心，但依赖注册中心提供的服务发现功能。
  - 服务注册：服务提供者启动时，将自身信息（IP、端口）注册到注册中心。
  - 服务发现：服务消费者通过注册中心查询目标服务的实例列表。
  - 实际调用：消费者根据获取的地址直接向服务提供者发起请求（如HTTP/RPC），​​不经过注册中心​

客户端可以通过注册中心发现服务实例。

2. 负载均衡：从注册中心获取**实例服务列表**，按照策略分发请求。
   - Ribbon：客户端负载均衡器，支持多种负载均衡策略：轮询、随机、加权轮询等。与Eureka/Nacos集成。
   - SpringCloud LoadBalancer：新一代负载均衡器，替代Ribbon。

负载均衡器从注册中心拉取目标服务的所有可用实例。根据策略（轮询、随机、权重）选择实例分发请求，不经过注册中心。


3. 服务通信：客户端与服务端之间的通信。
   - Feign/OpenFeign：声明式的HTTP客户端，基于接口注解简化HTTP调用，集成Ribbon。
   - Dubbo(Alibaba)：PRC远程调用框架，支持高性能远程调用。

4. 服务熔断：当服务调用失败或响应时间过长时，自动切换到备用服务。
   - Hystrix(Netflix )：熔断器，已停更。支持服务降级、熔断、限流等功能。
   - Sentinel(Alibaba)：流量控制、熔断降级，提供实时监控和规则配置。

5. API 网关：统一入口，处理鉴权、日志、请求路由转发功能。
   - Zuul(Netflix)：第一代API网关，已逐渐被淘汰。支持路由、过滤、鉴权等功能。
   - SpringCloud Gateway：新一代API网关，支持动态路由、限流、鉴权等功能。基于异步非阻塞模型。

6. 配置中心：避免配置文件分散在各个服务中，统一管理。
   - SpringCloud Config：集中化管理，支持Git/本地存储，需要配合Bus实现动态刷新。
   - Nacos Config：内置配置管理，支持多环境配置和实时推送。

7. 消息总线与事件驱动：
   - SpringCloud Bus：消息总线，配置变更后需手动刷新（配合 Spring Cloud Bus 可实现自动刷新）。
   - SpringCloud Stream：事件驱动，支持消息发布和订阅，通过消息中间件（如 Kafka、RabbitMQ）实现微服务间异步通信。

8. 分布式链路追踪
   - Sleuth：生成请求链路ID（Trace/Span）,集成Zipkin展示调用链。
   - Zipkin：分布式链路追踪系统，收集和可视化追踪数据，支持微服务之间的调用链追踪。

9. 分布式事务
   - TCC：Try-Confirm-Cancel，支持分布式事务的补偿机制。
   - Seata：提供AT、TCC等模式，解决跨服务数据一致性问题

### PRC与HTTP的区别？
PRC（Remote Procedure Call）远程过程调用，允许程序想调用本地方法一样调用远程服务，隐藏底层网络通信细节。开发者无需关心网络传输、序列化等底层实现，只需关注业务逻辑。

典型场景：微服务内部通信，高性能分布式系统（gRPC、Dubbo）

HTTP（Hypertext Transfer Protocol）超文本传输协议，应用层协议，用于用于客户端与服务器之间的资源传输。基于请求-响应模型，通过URL访问资源。

RPC的性能更高，使用二进制序列化，减少传输开销。而HTTP性能较低，使用文本格式（如json），解析效率低；
PRC的开发复杂度较高，需要处理序列化；HTTP开发复杂度低，基于标准协议，工具链成熟。





### Eureka和Nacos有什么区别？

### 为什么要选择微服务？微服务架构有哪些好处和缺点？

### 为什么要有分布式事务？


## MySQL

### 事务
事务（Transaction）：一组SQL语句组成的逻辑处理单元，这些操作要么全部成功，要么全部失败回滚。
在MySQL中主要保证数据的一致性，特别是在多用户并发的情况下。

事务四大特性：ACID
1. 原子性（Atomicity）：事务中的所有操作要么全部成功，要么全部失败回滚。
2. 一致性（Consistency）：事务执行前后，数据库的状态保持一致。
3. 隔离性（Isolation）：事务之间相互独立，互不干扰。
4. 持久性（Durability）：事务一旦提交，对数据库的修改是永久的。


**MySQL中默认采用自动提交模式，即每一条SQL语句都会自动作为一个独立的事务提交。**

显示事务控制：
- 显式开启事务：`START TRANSACTION`或`BEGIN`
- 提交事务：`COMMIT`
- 回滚事务：`ROLLBACK`

在MySQL中，只有**InnoDB**支持事务，MyISAM不支持。


### MySQL存储引擎

从MySQL5.7版本开始，InnoDB成为默认的存储引擎，MyISAM逐渐被淘汰。

1. InnoDB存储引擎：
   - 支持事务
   - 支持ACID
   - 支持行级锁
   - 支持外键
   - 支持MVCC（多版本并发控制）
   - 采用聚簇索引存储结构
   - 通过undo log实现事务回滚
   - 通过redo log保证事务持久性

2. MyISAM存储引擎：不支持事务、不支持行级锁（只能锁表）、不支持外键、不支持MVCC。

索引实现：
InnoDB使用`聚簇索引`，主键索引的叶子节点直接存储数据行.
MyISAM使用`非聚簇索引`，索引和数据分离存储。






### 什么是脏读、不可重复读、幻读？
在数据库系统中，事务的并发执行可能会引发多种数据一致性问题，其中最常见的就是脏读(Dirty Read)、不可重复读(Non-repeatable Read)和幻读(Phantom Read)。
1. **脏读（Dirty Read）**：脏读是指一个事务**读取**了另一个事务尚**未提交**的数据修改。如果读取到的数据在另一个事务中随后被**回滚**，那么该数据对当前事务来说就是"脏"的、不可靠的。脏读通常发生在​​读未提交(READ UNCOMMITTED)​​隔离级别下，这是最低的事务隔离级别。
2. **不可重复读（Non-repeatable Read）**：不可重复读是指在一个事务内多次读取同一数据时，**由于其他事务的修改并提交**，导致每次读取到的数据不一致。发生在​​读已提交(READ COMMITTED)​​隔离级别下。
3. **幻读（Phantom Read）**：在同一事务内，多次查询同一范围的数据，结果不一致。这是因为在事务执行过程中，**其他事务对数据进行了新增或删除**，导致第一次查询的数据与第二次查询的数据不一致。

总结：
脏读：读取到未提交的数据（数据未提交，可能回滚）
不可重复读：读取到已提交的数据，但是数据内容发生了变化（数据被其他事务修改），同一数据两次读取不一致。
幻读：读取到已提交的数据，但是数据数量发生了变化（数据增减），同一范围的数据两次查询不一致。

### MySQL事务的隔离级别？默认是什么？
MySQL 支持四种标准的事务隔离级别，按隔离强度从低到高依次为：
1. **读未提交（Read Uncommitted）**：允许读取未提交数据，一致性最差，性能最高。
2. **读已提交（Read Committed）**：仅读取已提交数据，避免脏读，性能较高。
3. **可重复读（Repeatable Read）**：同一事务内多次读取结果一致，**MySQL默认隔离级别**，性能中等。
4. **串行化（Serializable）**：提供严格的事务隔离，完全串行化执行，避免所有并发问题。性能最低。

| 隔离级别 | 脏读 | 不可重复读 | 幻读 |
| -------- | ---- | ---------- | ---- |
| ​​READ UNCOMMITTED​​	 读未提交  | 可能 | 可能       | 可能 |
| ​​READ COMMITTED​ 读已提交 | 不可能 | 可能       | 可能 |
| ​​REPEATABLE READ​​	可重复读 | 不可能 | 不可能     | 可能 |
| ​​SERIALIZABLE​ 串行化   | 不可能 | 不可能     | 不可能 |

默认隔离级别：
- MySQL：可重复读（Repeatable Read）
- Oracle：读已提交（Read Committed）


MySQL的可重复读通过MVCC（多版本并发控制）和Gap Lock（间隙锁）机制，在InnoDB存储引擎下基本解决了幻读问题。

### 什么是MVCC？
MySQL的**InnoDB**存储引擎可以在 **可重复读** 隔离级别下解决**幻读**问题：
1. MVCC（多版本并发控制）：通过创建事务开始时的数据快照来避免幻读。
2. 间隙锁：通过锁定查询范围内的记录和间隙来防止其他事务插入新记录。

### MySQL的索引类型
1. **B+树索引**：适用于等值查询和范围查询。
2. **哈希索引**：适用于等值查询。
3. **全文索引**：适用于全文搜索。
4. **空间索引**：适用于地理位置查询。

逻辑分类：
1. 普通索引
2. 唯一索引
3. 主键索引
4. 复合索引
5. 前缀索引

```sql
create index index_name on table_name(column_name);
create unique index ...
create index index_name on table_name(column1,column2);
drop index index_name on table_name;
```

### 索引失效的场景
1. 使用!= 或者 <> 操作符，非等值查询需要扫描大部分数据
2. 对索引列使用函数或表达式
3. 使用OR连接条件
4. 使用LIKE通配符查询
5. 违反最左匹配原则。联合索引(a,b,c)只能按a、(a,b)、(a,b,c)的顺序使用，跳过左侧列会导致索引失效
6. ​​范围查询后的列无法使用索引。在联合索引中，若某一列使用范围查询(>、<、BETWEEN)，其后的列无法使用索引


### 什么是聚簇索引和非聚簇索引
1. 聚簇索引：是一种​​将`数据存储与索引结合​`​的索引类型



2. 非聚簇索引：是一种​​将`索引与数据分开存储​`​的索引类型


### 索引存储结构 B树与B+树

B树：是一种平衡的多路搜索树，具有一下特点：
- 每个节点最多包含m个子节点
- 除根节点和叶子节点外，每个节点至少有⌈m/2⌉个子节点
- 所有叶子节点位于同一层
- 节点中的关键字按非递减顺序排列
- 关键字数量=子节点数-1

```text
        [10, 20]
       /    |    \
[5,8]  [15,18]  [25,30]
```


B+树：是B树的一种变体，具有以下特点：
- 非叶子节点只存储键值（索引信息），不存储数据
- 所有数据都存储在叶子节点中
- 叶子节点通过指针相连形成链表，便于范围查询

```text
        [10, 20]
       /    |    \
[5,8,10]->[15,18,20]->[25,30]
(叶子节点链表连接)
```

B+树的查询稳定，空间利用率较高，相同数据量下B+树更加矮壮，


MySQL InnoDB引擎 使用B+树作为存储结构
- 主键索引（聚簇索引）叶子几点存储完整数据记录
- 二级索引：叶子节点存储主键值


在InnoDB中，主键索引就是聚簇索引，而非主键索引都是非聚簇索引，查询时可能需要回表操作


为什么数据使用B+树？

1. 更适合磁盘I/O
2. 更高效的范围查询
3. 更高的查询稳定性
4. 更高的空间利用率

### MySQL索引建立原则

1. 选择合适的索引列：
    - 高频查询字段为经常出现在WHERE、GROUP BY、ORDER BY或JOIN子句中的字段创建索引，这些字段参与数据筛选和排序的频率高，索引能显著提升查询效率。
    - 不为NULL的字段：索引字段应尽量不为NULL

2. 避免不合理的索引设计：
   - 多个索引包含相同列组合时会产生冗余。例如已有索引(a,b)，再创建索引(a)就是冗余的。
   - 控制索引数量：索引会占用存储空间，并在数据写入时带来额外开销。一般建议单表索引不超过5-7个
   - 避免使用频繁更新的列作为主键


单列索引与复合索引：

- 单列索引​​：适合高频查询的单个字段
- 复合索引(联合索引)​​：当查询经常涉及多个字段组合时，复合索引比多个单列索引更高效
   - 最左前缀原则​​：索引(a,b,c)可支持a、(a,b)、(a,b,c)查询，但无法用于b、c或(b,c)查询。
   - 列顺序策略​​：高频查询列放左侧；等值查询列优先于范围查询列。
   - 长度控制​​：单个复合索引最好不要超过5-7列


- 尽量使用覆盖索引，避免回表操作
- 控制索引数量，避免过多索引影响写入性能
- 复合索引遵循最左匹配原则




### 慢查询定位与优化？

使用`EXPLAIN`命令分析SQL执行计划

关注列：

`type`：ALL(全表扫描)、index、range、ref、eq_ref、const
`key`：实际使用的索引
`rows`：预估扫描行数
`Extra`：Using filesort(需要额外排序)、Using temporary(使用临时表)



### binlog日志有用过吗？存的是什么？和redolog和undolog的区别？

### 为什么Delete语句执行比Insert语句慢？

### 有很多Delete语句可能导致主从同步延迟，为什么，怎么解决？


### 表t里有A~Z个字段，考虑为语句select a,b,c from t where d= 'xxx' order by e 建立一个最优索引

考虑一下建两种方式索引，分别说一下优缺点对上面的查询哪一个更高效点：
- d,a,b,c,e 建立两个索引
- d,e,a,b,c 只建一个索引



## Redis

### Redis的数据类型

1. String：缓存
2. Hash：商品详情页（存储商品对象字段:标题/价格/库存等）
3. List：
4. Set：去重无序集合，秒杀、抽奖（使用String库存计数，用Set实现用户去重）
5. Sorted Set： 带有Score分数的去重Set集合，应用：商品搜索（销量、评分等）、排行榜

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

### 排行榜应该用什么来实现？底层数据结构是什么？

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




## Kafka

### kafka有哪些应用场景

### Kafka什么时候会出现rebalabce？


### 消息队列消息挤压应该怎么办？



## 网络
### TCP/UDP


### 说一下TCP三次握手和四次挥手


### https和http的区别



### 有用到websocket吗？和http有什么区别？

## 设计模式

### JDK和spring中以及工作中常用的设计模式
JDk中：
- 单例模式：java.lang.Runtime#getRuntime()
- 工厂方法：java.util.Calendar#getInstance()
- 抽象工厂模式：
- 建造者模式：`StringBuilder`、java.nio.ByteBuffer
- 适配器模式：`java.util.Arrays#asList()`方法
- 装饰器模式：
- 代理模式：java.lang.reflect.Proxy
- 策略模式：java.util.Comparator、java.utils.concurrent.ThreadPoolExecutor中的`拒绝策略`
- 迭代器模式：java.util.Iterator


Spring框架中：
- 单例模式：Spring Bean默认作用域是单例的，`@Scope("singleton")`
- 工厂模式：BeanFactory、ApplicationContext
- 代理模式：AOP的实现基础，ProxyFactoryBean
- 模版方法模式：JdbcTemplate、RestTemplate
- 适配器模式： Spring MVC 的HandlerAdapter处理器适配器


工作中常用的设计模式：
- 策略模式：比如定义支付方式接口，实现支付宝和微信两种支付方式
- 工厂模式：比如报表生成器，可以返回PDF、EXCEL、CSV等不同格式的报表生成器
- 责任链模式：比如审批流程处理，定义Approver抽象类，定义Approver next属性，定义具体的Approver实现类，比如Leader、Manager、Director等。
-建造者模式：比如复杂对象构建，定义static静态的Builder类，定义setter方法，定义build方法，返回对象。


设计模式是解决问题的工具而非目标，在实际开发中应当根据具体场景合理运用。理解这些模式的本质思想比记住具体实现更为重要。
### 单例模式有什么好处？

### 项目中用到哪些设计模式？



## 场景题
### 如何设计高并发秒杀？

### 秒杀下单过程中，用户认为效果很好，想增加优惠券的数量，应该怎么做？

## 其他

### 谈谈项目的难点

### 非对称加密算法有哪些？

平时有看过哪些源码？

平时通过什么途径学习？

对自己未来规划

遇到过什么挑战？如何回答？