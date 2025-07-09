# Spring
Spring的核心是`IOC`(Inversion of Control)**控制反转** 和`AOP`(Aspect-Oriebted Programming)面向切面编程。

### 什么是IOC？实现原理？
`IOC`(Inversion of Control)**控制反转** 是面向对象编程中的一种设计原则，也是Spring框架的核心思想之一，用于降低代码之间的耦合度。
**将创建对象的将控制权交给容器来管理。**

控制反转的含义:
- 传统控制流程：由应用程序主动创建和管理依赖对象
- IOC控制流程：将对象的创建和依赖管理的控制权交给容器（框架），应用程序只需要声明依赖关系，容器负责创建和管理对象。

控制反转意味着将原本由程序代码直接操控的对象创建和依赖管理权交给外部容器（如Spring容器）来管理。

为什么需要IOC：
降低代码耦合度
可维护性：修改依赖关系时无需改动大量代码
可测试性：依赖可通过Mock注入，便于单元测试
提高代码复用性避免重复创建。


`DI`(Dependency Injection)依赖注入是`IOC`的一种实现方式，通过构造函数、Setter 方法或字段注入依赖对象。

### IOC容器有哪些？

- BeanFactory：Spring最早的IOC容器，提供了基本的IOC功能，延迟加载Bean。
- ApplicationContext：BeanFactory的子接口，提供了更多的企业级功能，如国际化、事件传播、资源访问等。

### Bean的作用域

Spring Bean默认是单例的，即每个Bean在整个应用程序中只有一个实例。

除了单例作用域，Spring还支持以下作用域：
- Prototype：每次请求都会创建一个新的Bean实例。
- Request：每个HTTP请求都会创建一个新的Bean实例，该作用域仅适用于Web应用程序。
- Session：每个HTTP Session都会创建一个新的Bean实例，该作用域仅适用于Web应用程序。
- Global Session：每个全局的HTTP Session都会创建一个新的Bean实例，该作用域仅适用于Portlet应用程序。

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

Spring AOP 基于动态代理实现：具体分为两种方式：
JDK动态代理：只能代理接口，Spring AOP默认使用JDK动态代理。使用反射实现动态代理
CGLIB动态代理：可以代理类，也可以代理接口，当Bean没有实现接口时，Spring AOP会使用CGLIB动态代理。使用字节码技术生成一个新的类，该类继承自目标类，并重写其中的方法。

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

### 什么是动态代理？为什么要使用动态代理？为什么不直接使用目标类方法？


动态代理是一种在运行时动态生成代理对象的技术，通过拦截目标对象的方法调用，在不修改原始代码的前提下实现功能增强。


使用动态代理的核心目的是在不修改原始代码的前提下，解耦核心业务逻辑与通用功能

为什么要使用动态代理？而不直接使用目标类方法？

1. 避免代码臃肿：若直接调用目标类方法，所有通用逻辑（如日志记录）需在每个方法中重复编写，导致代码臃肿且难以维护。
2. 功能动态扩展：直接调用目标类时，新增功能（如性能监控）需修改所有相关方法，违反开闭原则。通过代理类灵活插入新功能，无需改动原有代码。


### Spring MVC的执行流程
`Spring MVC` 的核心流程围绕 `前端控制器`（`DispatcherServlet`） 展开，将请求分发给对应的处理器（`Controller`），并最终`渲染视图`返回给客户端。

1. **首先请求到达 `DispatcherServlet` 前端控制器**：所有的HTTP请求首先有DispatcherServlet 接收，它是Spring MVC的核心调度器。
2. **调用 `HandlerMapping` 处理器映射器**：DispatcherServlet 会调用 HandlerMapping 处理器映射器，根据请求的URL找到对应的处理器（`Controller`）。
3. **调用 `HandlerAdapter` 处理器适配器**：DispatcherServlet 通过 HandlerAdapter（处理器适配器）调用具体的Controller方法。
4. **执行 `Controller` 方法**：HandlerAdapter 使用 ArgumentResolver（参数解析器）解析方法参数（如 @RequestParam、@RequestBody 等）。返回结果（ModelAndView）
5. **处理返回值**：
   -  `ViewResolver 视图解析器` 解析视图（将逻辑视图名转换为实际视图），渲染视图（使用模型数据渲染视图）：使用模型（Model）渲染视图（View），将Model中的数据填充到视图中；
   - 或者直接响应数据，使用 `HttpMessageConverter` 将返回值转换为JSON/XML等格式，直接写入响应流。
6. **返回响应给客户端**：DispatcherServlet 将处理结果返回给客户端。

## SpringBoot 与SpringCloud的区别


## Spring Boot的自动配置原理