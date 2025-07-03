# 设计模式


## 单例模式

### 饿汉式

饿汉式单例模式在类加载时就创建了实例，确保线程安全，但可能会浪费资源，因为即使没有使用实例也会创建。
私有化构造方法，提供一个静态方法获取实例。

```java
public class Singleton1{
    private static Singleton1 instance = new Singleton1();
    private Singleton1(){}
    public static Singleton1 getInstance(){
        return instance;
    }
}
```

### 懒汉式
```java
public class Singleton2{
    private static volatile Singleton2 instance;
    private Singleton2(){}
    
    public static Singleton2 getInstance(){
        if (instance == null) {
            synchronized (Singleton2.class) {
                if (instance == null) {
                    instance = new Singleton2();
                }
            }
        }
        return instance;
    }
}
```
在懒汉式单例模式中，使用 `synchronized` 和两次 `if (instance == null)` 判断是为了确保线程安全，同时提高性能。以下是原因：

1. **线程安全**：
    - 在多线程环境下，如果多个线程同时进入 `getInstance()` 方法并且 `instance` 为 `null`，可能会导致多个线程同时创建实例。通过 `synchronized` 块，可以确保只有一个线程能够进入临界区，从而避免实例被多次创建。

2. **双重检查锁定（Double-Checked Locking）**：
    - 第一次 `if (instance == null)` 是为了避免不必要的同步开销。如果实例已经创建，直接返回实例，不需要进入 `synchronized` 块。
    - 第二次 `if (instance == null)` 是为了防止多个线程在第一次检查后同时进入 `synchronized` 块。只有在 `synchronized` 块内再次确认 `instance` 为 `null` 时，才会创建实例。

3. **性能优化**：
    - 如果没有双重检查，每次调用 `getInstance()` 都需要进入同步块，这会导致性能下降。双重检查锁定可以减少同步操作的次数，从而提高性能。

总结：双重检查锁定结合 `synchronized` 的使用，既保证了线程安全，又避免了不必要的性能损耗。

### 静态内部类
```java
public class Singleton3{
    private Singleton(){}
    
    private static class Singleton3Holder{
        private static final Singleton3 INSTANCE = new Singleton3();
    }
    
    public static Songleton3 getInstance(){
        return Singleton3Holder.INSTANCE;
    }
}
```

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
- 建造者模式：比如复杂对象构建，定义static静态的Builder类，定义setter方法，定义build方法，返回对象。
- 适配器模式：


设计模式是解决问题的工具而非目标，在实际开发中应当根据具体场景合理运用。理解这些模式的本质思想比记住具体实现更为重要。

## 策略模式



## 