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

## 缓存击穿、缓存穿透、缓存雪崩

## 分布式锁


# RabbitMQ

## 面试题
- RabbitMQ如何保证消息不丢失（消息可靠性）？
- RabbitMQ如何保证消息不重复消费？
- RabbitMQ如何保证消息的顺序性？