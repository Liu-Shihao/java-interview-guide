# Java并发与多线程


# 面试题

- 线程与进程的区别
- 并行与并发的区别
- 线程创建的方式有哪些？runnable和callable的区别？start()和run()的区别？
- 线程有哪些状态？状态之间是如何改变的？
- wait()和sleep()的区别
- 如何停止一个正在运行的线程？
- 说一下synchronized，synchronized锁升级的过程
- 什么是JMM？
- 说一下volatile的作用、实现原理和适用场景
- synchronized和lock的区别
- 线程池的核心参数有哪些？拒绝策略有哪些？常见的阻塞队列有哪些？
- 说一下线程池的工作流程
- 谈谈你对ThreadLocal的理解
- ThreadLocal为什么会出现内存泄漏

# 线程与进程
线程与进程

- 进程：是操作系统分配资源的基本单位
- 线程：CPU调度的基本单位，一个进程可以包含多个线程

并发与并行
- 并发：多个任务在同一时间段内交替执行
- 并行：多个任务在同一时刻同时执行，通常需要多核CPU支持


# 线程创建的方式
1. 继承Thread类，重写run方法
    ```java
    class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("线程正在运行。");
        }
        
        public static void main(String[] args) {
            MyThread thread = new MyThread();
            thread.start(); // 启动线程
        }
    }
    ```
2. 实现Runnable接口，重写run方法
    ```java
    class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("线程正在运行。");
        }

        public static void main(String[] args) {
            MyRunnable runnable = new MyRunnable();
            Thread thread = new Thread(runnable);
            thread.start(); // 启动线程
        }
    }
    ```
3. 实现Callable接口，重写call方法，需要配合FutureTask使用
    ```java
    class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "线程正在运行。";
        }
        public static void main(String[] args) {
            MyCallable callable = new MyCallable();
            FutureTask<String> futureTask = new FutureTask<>(callable);
            Thread thread = new Thread(futureTask);
            thread.start(); // 启动线程
            try {
                String result = futureTask.get(); // 获取线程执行结果
                System.out.println(result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    ```
4. 使用lambda表达式
    ```java
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("线程正在运行。");
        });
        thread.start(); // 启动线程
    }
    ```
5. 使用线程池
    ```java
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    executorService.submit(() -> {
        System.out.println("线程正在运行。");
    });
    executorService.shutdown(); // 关闭线程池
    ```
## start()方法与run()方法的区别

| 特性 | start()方法 | run()方法 |
|------|------------|----------|
| 线程创建 | 创建新线程 | 不创建新线程 |
| 执行方式 | 异步执行 | 同步执行 |
| 调用次数 | 每个线程对象只能调用一次 | 可多次调用 |
| 并发性 | 实现多线程并发 | 普通方法调用 |
| JVM处理 | 调用native方法start0() | 直接执行方法体 |
| 线程状态 | 使线程进入RUNNABLE状态 | 不影响线程状态 |

**关键区别**：
- `start()`方法会启动一个新线程，并在新线程中执行`run()`方法
- 直接调用`run()`方法不会创建新线程，而是在当前线程中同步执行
- `start()`方法只能调用一次，否则会抛出`IllegalThreadStateException`

正确启动线程的方式应该是调用`start()`方法，而不是直接调用`run()`方法。

# 线程池
线程池是一种管理和复用线程的机制，它可以有效地控制线程的数量，避免线程频繁创建和销毁的开销，提高系统性能和响应速度。

## 线程池核心参数
| 参数 | 含义 |
|------|------|
| corePoolSize | 核心线程数：线程池中保持的线程数，即使这些线程处于空闲状态，也不会被销毁。 |
| maximumPoolSize | 最大线程数：线程池中允许的最大线程数，当核心线程数已满且任务队列已满时，线程池会创建新的线程，直到达到最大线程数。 |
| keepAliveTime | 线程空闲时间：当线程数超过核心线程数时，多余的空闲线程在终止前等待新任务的最长时间。 |
| unit | 时间单位 |
| workQueue | 任务队列：用于存放待执行任务的队列，当核心线程数已满且任务队列已满时，新的任务会被拒绝。 |
| threadFactory | 线程工厂：用于创建新线程的工厂，通常用于设置线程的优先级、名称等属性。 |
| handler | 拒绝策略：当线程池和任务队列都满时，新任务的处理策略，如直接拒绝、丢弃旧任务、丢弃新任务等。 |

## 线程池工作流程
1. 线程池创建时，会创建corePoolSize个核心线程
2. 当有新任务提交时，会判断当前线程数是否小于corePoolSize，如果是，则创建新线程执行任务
3. 如果当前线程数大于corePoolSize，则将任务放入workQueue中
4. 如果workQueue已满，则创建新线程执行任务，直到线程数达到maximumPoolSize
5. 如果线程数达到maximumPoolSize，且workQueue已满，则根据拒绝策略处理新任务
6. 当线程空闲时间超过keepAliveTime时，线程会被销毁

## 常用的阻塞队列
1. **ArrayBlockingQueue**：基于数组的有界阻塞队列
2. **LinkedBlockingQueue**：基于链表的无界阻塞队列
3. **PriorityBlockingQueue**：基于优先级的无界阻塞队列
4. **SynchronousQueue**：不存储元素的阻塞队列，每个插入操作必须等待另一个线程的移除操作


## 线程池拒绝策略
1. **AbortPolicy**：直接抛出RejectedExecutionException异常，阻止系统正常工作
2. **CallerRunsPolicy**：由调用线程处理该任务
3. **DiscardPolicy**：丢弃任务，不抛出异常
4. **DiscardOldestPolicy**：丢弃队列中等待最久的任务，然后把当前任务加入队列中

## 线程池的优点
1. **提高性能**：通过复用线程，避免了线程频繁创建和销毁的开销
2. **提高响应速度**：通过线程复用，减少了线程创建和初始化的时间
3. **提高线程的可管理性**：线程池可以统一分配、调度和监控线程，避免资源泄露
4. **提供更强大的功能**：线程池可以根据业务需求动态调整线程池大小，实现线程的动态伸缩


## 常见线程池
1. **FixedThreadPool**：固定大小的线程池，适用于执行大量短期任务
2. **CachedThreadPool**：可缓存的线程池，线程数量根据任务动态调整
3. **SingleThreadExecutor**：单线程的线程池，适用于需要顺序执行任务的场景
4. **ScheduledThreadPool**：定时任务线程池，支持定时执行任务
5. **WorkStealingPool**：工作窃取线程池，适用于多核CPU环境

```java
ExecutorService executorService = Executors.newFixedThreadPool(5);
executorService.submit(() -> {
    System.out.println("线程正在运行。");
});
executorService.shutdown(); // 关闭线程池

// 自定义线程池
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    5, // 核心线程数
    10, // 最大线程数
    60, // 空闲线程存活时间
    TimeUnit.SECONDS, // 时间单位
    new ArrayBlockingQueue<>(100), // 任务队列
    Executors.defaultThreadFactory(), // 线程工厂
    new ThreadPoolExecutor.AbortPolicy() // 拒绝策略
)
```
## 线程池参数配置
1. **核心线程数**：根据CPU核心数设置，一般为CPU核心数
2. **最大线程数**：根据业务需求设置，一般为CPU核心数*2
3. **空闲线程存活时间**：根据业务需求设置，一般为60秒
4. **任务队列**：根据业务需求设置，一般为ArrayBlockingQueue或LinkedBlockingQueue
5. **拒绝策略**：根据业务需求设置，一般为AbortPolicy或DiscardPolicy

# 线程状态
1. **新建状态(NEW)**：线程对象被创建但尚未启动
2. **就绪状态(RUNNABLE)**：线程已启动，等待CPU时间片
3. **运行状态(RUNNING)**：线程获得CPU时间片正在执行（属于RUNNABLE的子状态）
4. **阻塞状态(BLOCKED)**：线程等待获取监视器锁
5. **等待状态(WAITING)**：线程无限期等待，直到被其他线程显式唤醒
6. **计时等待状态(TIMED_WAITING)**：线程在指定时间内等待
7. **终止状态(TERMINATED)**：线程执行完毕或异常终止


在Java中，线程的阻塞状态(BLOCKED)和等待状态(WAITING)是两种不同的线程状态，主要区别如下：

### 阻塞状态(BLOCKED)
1. **定义**：线程因等待获取对象监视器锁(如synchronized锁)而进入的状态
2. **触发条件**：
   - 尝试进入同步代码块时锁被其他线程持有
   - 只有synchronized关键字会导致线程进入此状态
3. **特点**：
   - 被动等待，由JVM调度器决定唤醒时机
   - 不响应中断
   - 获得锁后自动转为RUNNABLE状态

### 等待状态(WAITING)
1. **定义**：线程主动放弃CPU执行权，等待其他线程显式唤醒的状态
2. **触发条件**：
   - 调用Object.wait()
   - 调用Thread.join()
   - 调用LockSupport.park()
3. **特点**：
   - 主动等待，需要其他线程显式唤醒(如notify()/notifyAll())
   - 可响应中断
   - 唤醒后需重新竞争锁

### 关键区别
1. **唤醒机制**：
   - 阻塞状态由JVM自动管理
   - 等待状态需其他线程显式唤醒

2. **锁行为**：
   - 阻塞状态线程仍在竞争锁
   - wait()会释放已持有的锁

3. **应用场景**：
   - 阻塞状态主要用于同步代码块互斥
   - 等待状态用于线程间协调通信

状态转换示例：
```
RUNNABLE --synchronized(锁被占用)--> BLOCKED
RUNNABLE --wait()/join()--> WAITING
WAITING --notify()/interrupt()--> RUNNABLE/BLOCKED
```

注意：使用JUC包中的Lock时，线程等待锁会进入WAITING状态而非BLOCKED状态。

# 线程间通信
1. **wait()/notify()**：用于线程间的等待和唤醒
   ```java
   synchronized(obj) {
        while(条件不满足) {
            obj.wait();
        }
        // 执行操作
        obj.notifyAll();
    }
   ```
2. Condition接口
   ```java
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    lock.lock();
    try {
        while(条件不满足) {
            condition.await();
        }
        // 执行操作
        condition.signalAll();
    } finally {
        lock.unlock();
    }
   ```
3. BlockQueue阻塞队列
    ```java
    BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    // 生产者
    queue.put("item");
    // 消费者
    String item = queue.take();
    ```

# Java内存模型（JMM）
Java内存模型(Java Memory Model, JMM)是Java并发编程的核心基础，它**定义了多线程环境下变量的访问规则，以及线程如何通过内存进行交互。**

## JMM核心概念
1. 主内存与工作内存
   - 主内存（Main Memory）：所有线程共享的内存区域，包括共享变量、静态变量等。
   - 工作内存（Working Memory）：每个线程私有的内存区域，包括局部变量、方法参数等。
   ```
   线程A的工作内存 ←→ 主内存 ←→ 线程B的工作内存
   ```
2. 内存间的交互操作
    读：从主内存读取变量到工作内存；
    写：从工作内存写回变量到主内存
   JMM定义了8种原子操作来完成主内存与工作内存的交互：
   - lock 锁定：锁定一个变量，使其他线程无法访问该变量
   - unlock 解锁：解锁一个变量，允许其他线程访问该变量
   - read 读取：从主内存读取变量到工作内存
   - load 加载：将变量从主内存复制到工作内存
   - use 使用：从工作内存读取变量
   - assign 赋值：将变量从工作内存赋值给其他变量
   - store 存储：将变量从工作内存存储到主内存
   - write 写入：将变量的值写入主内存


## JMM三大特性：


Java内存模型(JMM)主要为了解决多线程编程中的三大核心问题：
1. **可见性(Visibility)**：一个线程对共享变量的修改，另一个线程无法立即看到。
   
   根本原因：
    - CPU缓存架构（多级缓存）
    - 编译器优化导致的指令重排序
    - 线程工作内存与主内存不一致
   
   解决方法：
    - volatile关键字：保证变量的可见性
    - synchronized关键字：保证同一时间只有一个线程执行被修饰的代码块
    - Lock接口：保证同一时间只有一个线程执行被修饰的代码块
2. **有序性(Ordering)**： 程序执行顺序与代码编写顺序不一致
   
   根本原因：
    - 编译器优化导致的指令重排序
    - 处理器优化导致的指令重排序
   
   解决方法：
    - volatile关键字：禁止指令重排序
    - synchronized关键字：保证同一时间只有一个线程执行被修饰的代码块

3. **原子性(Atomicity)**：一个操作在执行过程中不可被中断，要么全部执行成功，要么全部执行失败。
   
   根本原因：
    - 多线程交错执行
    - 非原子性操作（如i++实际是读-改-写三步操作）
   
   解决方法：
    - 锁机制：synchronized、Lock等
    - CAS算法：Compare And Swap，比较并交换，保证操作的原子性


## happens-before原则
happens-before原则JMM定义的关键规则，判断数据是否存在竞争：
1. **程序顺序规则**：同一线程内的操作按代码顺序执行
2. **监视器锁规则**：解锁操作先于后续的加锁操作
3. **volatile规则**：volatile写操作先于后续的读操作
4. **线程启动规则**：Thread.start()先于线程内的任何操作
5. **线程终止规则**：线程的所有操作先于终止检测
6. **线程中断规则**：interrupt()调用先于被中断线程检测到中断
7. **对象终结规则**：构造函数执行结束先于finalize()方法
8. **传递性**：如果A happens-before B，且B happens-before C，则A happens-before C。



## 内存屏障类型
1. **LoadLoad屏障**：确保load1数据的装载先于load2及其后所有装载指令的装载
2. **StoreStore屏障**：确保store1数据对其他处理器可见（刷新到内存）先于store2及其后所有存储指令的存储
3. **LoadStore屏障**：确保load1数据的装载先于store2及其后所有的存储指令刷新到内存
4. **StoreLoad屏障**：确保store1数据对其他处理器可见（刷新到内存）先于load2及其后所有装载指令的装载(全能屏障)






# 线程同步机制

1. **synchronized关键字**：用于方法或代码块，确保同一时间只有一个线程可以执行被修饰的方法或代码块。
2. **Lock接口及其实现类**：提供了显式的锁机制，包括ReentrantLock、ReadWriteLock等。
3. **volatile关键字**：用于修饰变量，确保变量的可见性和有序性。
4. **原子类(AtomicXXX)**：提供了线程安全的基本数据类型操作，如AtomicInteger、AtomicLong等。
5. **并发集合类**：如ConcurrentHashMap、CopyOnWriteArrayList等，提供了线程安全的集合操作。


## Synchronized
`synchronized` 是 Java 中用于实现线程同步的关键字，它可以确保多个线程在访问共享资源时的线程安全性。

synchronized 的主要作用是：
1. 保证同一时刻只有一个线程可以执行某个方法或代码块
2. 确保线程对变量的修改能够及时对其他线程可见
3. 建立 happens-before 关系，保证代码执行顺序

`synchronized`关键字用于方法或代码块，确保同一时间只有一个线程可以执行被修饰的方法或代码块。它有两种使用方式：
1. **同步方法**：在方法声明前加上`synchronized`关键字，确保同一时间只有一个线程可以执行该方法。
如果修饰的是示例方法锁对象是当前实例对象this，同一实例的同步方法在同一时间只能被一个线程访问；
如果修饰的是静态方法锁对象是当前类的Class对象，所有实例的静态同步方法在同一时间只能被一个线程访问。
```java
    private int count;
    /**
     * synchronized修饰示例方法
     * 锁对象是当前实例（this）
     */
    public synchronized void increment(){
        count++;
    }

    private static int numStatic;
    /**
     * 修饰静态方法
     * 锁对象是类的Class对象（Class02_Synchronized.class）
     * 可以缩小同步范围，提高性能
     */
    public static  synchronized void update(){
        numStatic++;
    }
```


2. **同步代码块**：在代码块前加上`synchronized`关键字，括号内指定锁对象，确保同一时间只有一个线程可以执行该代码块。
```java
    private final Object lock = new Object();
    /**
     * synchronized修饰代码块
     * 锁对象可以指定任意对象（可以是实例或者Class对象）
     */
    public int syncCodeBlock(){
        synchronized(lock) {
            return count++;
        }
    }
```

注意：`synchronized`可以修饰方法或代码块，但是不能修饰属性。（编译错误）



## Synchronized锁升级的过程
`synchronized`关键字在Java中是一种锁机制，它可以保证在同一时间只有一个线程可以执行被修饰的方法或代码块。synchronized锁升级是Java 6(JSR 133)引入的重要优化，旨在减少同步操作的开销。锁升级过程根据竞争情况从无锁状态逐步升级到重量级锁，这一机制显著提升了synchronized的性能。
`synchronized`关键字的实现是基于Java对象头中的`Mark Word`来实现的。
`Mark Word`是Java对象头中的一部分，用于存储对象的状态信息，包括锁信息、哈希码、GC信息等。
`synchronized`关键字的锁升级过程如下：

无锁 → 偏向锁 → 轻量级锁 → 重量级锁
1. **无锁**：对象的`Mark Word`中存储了对象的哈希码、GC信息等，无锁状态（01）。
2. **偏向锁**：当只有一个线程获取锁时，会将对象的`Mark Word`中的线程ID设置为当前线程的ID，同时将对象的状态设置为偏向锁（01）。  
实现原理：
第一个访问的线程通过CAS将Mark Word中的线程ID替换为自己的ID。之后该线程进入同步块只需检查线程ID是否匹配，匹配则直接执行，不匹配说明有竞争，升级为轻量级锁  
3. **轻量级锁**：当有多个线程同时获取锁时，偏向锁会升级为轻量级锁。轻量级锁使用CAS操作来尝试获取锁，如果成功则将对象的`Mark Word`中的线程ID设置为当前线程的ID，同时将对象的状态设置为轻量级锁（00）。失败则自旋尝试，自旋超过阈值或第三个线程来竞争，升级为重量级锁。
4. **重量级锁**：当有多个线程同时获取锁时，轻量级锁会升级为重量级锁（10）。重量级锁使用操作系统的互斥量来实现，当一个线程获取锁时，会阻塞其他线程的访问。    
实现原理：
对象关联一个监视器(Monitor)对象，未获取锁的线程进入阻塞队列。依赖操作系统的互斥量(mutex)实现
缺点：涉及用户态到内核态的切换，开销大     

```
无锁(01)
  │
  ▼ (第一个线程访问)
偏向锁(01) → 有竞争 → 轻量级锁(00)
                  │
                  ▼ (自旋失败/多线程竞争)
              重量级锁(10)
```


锁升级的意义
减少性能开销：无竞争时几乎零成本
适应不同场景：根据竞争强度动态调整
平滑过渡：避免直接使用重量级锁的开销
提高吞吐量：通过自旋减少线程切换

锁升级机制使得synchronized在低竞争场景下性能接近无锁，在高竞争场景下仍能保证正确性，是Java并发性能优化的重要里程碑。


## ReentrantLock
Java 中的 `Lock` 接口是 Java 并发包 (java.util.concurrent.locks) 中提供的一个比内置 `synchronized` 关键字更灵活的线程同步机制。

`Lock`接口及其实现类提供了显式的锁机制，包括`ReentrantLock`、`ReadWriteLock`等。
- **ReentrantLock**：可重入锁，提供了与`synchronized`关键字类似的功能，但更加灵活和可扩展。
```java
Lock lock = new ReentrantLock();
try {
    lock.lock();
    // 临界区代码
} finally {
    lock.unlock(); // 确保在finally块中释放锁
}
```
- **ReadWriteLock**：读写锁，允许多个线程同时读取共享资源，但只允许一个线程写入。
```java
ReadWriteLock rwLock = new ReentrantReadWriteLock();
Lock readLock = rwLock.readLock();
Lock writeLock = rwLock.writeLock();

// 读操作
readLock.lock();
try {
    // 读取共享数据
} finally {
    readLock.unlock();
}

// 写操作
writeLock.lock();
try {
    // 修改共享数据
} finally {
    writeLock.unlock();
}
```

注意：使用 Lock 时必须手动释放锁，通常在 finally 块中调用 unlock() 以确保锁被释放。

## Volatile关键字
`volatile` 是 Java 中用于**保证变量可见性和禁止指令重排序的关键字**，它是 Java 轻量级的同步机制之一。

当一个变量被声明为`volatile`时，
- 写volatile变量时，立即刷新到主内存
- 读volatile变量时，从主内存重新加载

核心特性：
1. **保证变量可见性**：当一个变量被声明为 volatile 时，对该变量的修改会立即刷新到主内存中，而对该变量的读取也会从主内存中获取最新的值。
2. **禁止指令重排序**：JVM 和 CPU 会对指令进行优化重排，volatile 关键字通过插入内存屏障(Memory Barrier)实现了禁止指令重排序，保证了代码的执行顺序。


实现原理
JVM 会在 `volatile` 变量操作前后插入`内存屏障`：

1. **写操作前**：StoreStore 屏障
2. **写操作后**：StoreLoad 屏障
3. **读操作前**：LoadLoad 屏障 + LoadStore 屏障



使用 volatile 关键字的场景：
1. **状态标志**：当一个变量的值可能被多个线程修改时，可以使用 volatile 来确保变量的可见性。
2. **双重检查锁定**：在单例模式中，使用 volatile 可以确保双重检查锁定的正确性。
3. **计数器**：当一个变量的值需要被多个线程累加时，可以使用 volatile 来确保变量的可见性。
4. **标志位**：当一个变量的值需要被多个线程修改时，可以使用 volatile 来确保变量的可见性。


状态标志:
```java
public class ShutdownRequested {
    private volatile boolean shutdownRequested;
    
    public void shutdown() {
        shutdownRequested = true;
    }
    
    public void doWork() {
        while (!shutdownRequested) {
            // 执行任务
        }
    }
}
```

单例模式的双重检查锁定(DCL):
```java
public class Singleton {
    private static volatile Singleton instance;
    
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```


### Volatile 与 synchronized
1. **可见性**：volatile 保证了变量的可见性，即当一个线程修改了 volatile 变量的值，其他线程能够立即看到这个变化。synchronized 也能保证可见性，因为它会在释放锁之前刷新所有变量的修改到主内存。
2. **原子性**：volatile 不能保证操作的原子性，而 synchronized 可以保证操作的原子性。
3. **有序性**：volatile 禁止了指令重排序，保证有序性； synchronized 也可以保证有序性。 




---
volatile总结：
1. 保证变量可见性
2. 禁止指令重排序
3. volatile保证可见性和有序性，但不保证原子性。
 
 



## Atomic原子类
Java 原子类是 `java.util.concurrent.atomic` 包下的一组类，它们提供了一种线程安全的方式来操作单个变量，无需使用同步锁（如 `synchronized`）。
原子类(AtomicXXX)提供了线程安全的基本数据类型操作，如`AtomicInteger`、`AtomicLong`等。这些类的方法都是原子操作，即执行过程中不会被其他线程打断。

核心特点：
1. **无锁线程安全**：基于 `CAS` (Compare-And-Swap) 实现，而非传统锁机制
2. **高性能**：避免了线程阻塞和上下文切换的开销
3. **原子性保证**：单个操作是原子的
4. **内存可见性**：保证变量的修改对其他线程立即可见


基本类型原子类
- `AtomicBoolean`：原子更新布尔类型
- `AtomicInteger`：原子更新整型
- `AtomicLong`：原子更新长整型

引用类型原子类
- `AtomicReference`：原子更新引用类型
- `AtomicStampedReference`：带版本号的原子引用（解决ABA问题）
- `AtomicMarkableReference`：带标记位的原子引用

数组原子类
- `AtomicIntegerArray`：原子更新整型数组元素
- `AtomicLongArray`：原子更新长整型数组元素
- `AtomicReferenceArray`：原子更新引用类型数组元素

字段更新原子类
- `AtomicIntegerFieldUpdater`：原子更新对象的整型字段
- `AtomicLongFieldUpdater`：原子更新对象的长整型字段
- `AtomicReferenceFieldUpdater`：原子更新对象的引用字段


所有原子类都提供以下核心方法：

    ```java
        // 获取当前值
        get()

        // 设置新值
        set(newValue)

        // 原子设置新值并返回旧值
        getAndSet(newValue)

        // CAS操作（Compare-And-Swap）
        compareAndSet(expect, update)  // 如果当前值==expect，则设置为update

        // 原子递增/递减
        getAndIncrement()  // i++
        getAndDecrement()  // i--
        incrementAndGet()  // ++i
        decrementAndGet()  // --i

        // 原子加减
        getAndAdd(delta)   // 返回旧值，然后加delta
        addAndGet(delta)   // 加delta，返回新值
    ```

原子类的核心是 **CAS (Compare-And-Swap)** 操作：

    ```java
        // 伪代码表示CAS操作
        public boolean compareAndSet(int expect, int update) {
            if (this.value == expect) {
                this.value = update;
                return true;
            }
            return false;
        }
    ```

Java原子类为并发编程提供了高效、轻量级的线程安全解决方案，是构建高性能并发系统的重要工具。
现代CPU都提供了CAS指令（如x86的`CMPXCHG`），JVM会将这些操作映射为本地硬件指令。

最佳实践：
1. 优先使用原子类而非 `volatile` + 同步的组合
2. 对于简单计数器、状态标志等场景非常适用
3. 高竞争环境下考虑使用 `LongAdder` (Java8+) 替代 `AtomicLong`
4. 复杂操作仍需使用锁或其他同步机制

# 并发工具类

## CountDownLatch 计时器门闩
`CountDownLatch` 是一个同步工具类，它允许一个或多个线程等待其他线程完成操作后再继续执行。
CountDownLatch 内部维护了一个计数器，当计数器的值为0时，表示所有等待的线程都已经完成操作，可以继续执行。

CountDownLatch 基于计数器实现，主要特点包括：
- **初始化计数器**：构造时指定计数值
- **等待机制**：调用 `await()` 的线程会阻塞，直到计数器减到0
- **计数递减**：其他线程完成任务后调用 `countDown()` 减少计数
- **一次性使用**：计数器无法重置，用完即废弃

主要方法：
```java
    // 构造方法 - 初始化计数器
    CountDownLatch(int count)

    // 使当前线程等待，直到计数器减到0
    void await()

    // 带超时的等待
    boolean await(long timeout, TimeUnit unit)

    // 减少计数器值（计数-1）
    void countDown()

    // 获取当前计数值
    long getCount()
```

CountDownLatch 代码示例：
```java
// 创建计数器(3个子任务)
CountDownLatch latch = new CountDownLatch(3);

for (int i = 0; i < 3; i++) {
    new Thread(() -> {
        try {
            // 模拟任务执行
            Thread.sleep((long)(Math.random() * 1000));
            System.out.println(Thread.currentThread().getName() + " 完成任务");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            latch.countDown(); // 任务完成，计数器减1
        }
    }).start();
}

try {
    System.out.println("主线程等待子线程完成任务...");
    latch.await(); // 主线程等待所有任务完成
    System.out.println("所有子线程完成任务，主线程继续执行");
} catch (InterruptedException e) {
    e.printStackTrace();
}
```
使用注意事项：
1. **一次性使用**：计数器减到0后无法重置，如需重复使用考虑 `CyclicBarrier`
2. **异常处理**：确保在 `finally` 块中调用 `countDown()`，避免线程异常导致计数器无法减少
3. **避免死锁**：确保计数器最终能减到0，否则等待线程会一直阻塞
4. **性能考虑**：对于极高并发场景，`countDown()` 调用不会阻塞，性能较好


实际应用场景：
1. 并行任务拆分后的汇总
2. 服务启动时等待所有组件初始化完成
3. 测试并发代码时协调多个线程同时开始
4. 批量处理任务时等待所有任务完成

## CyclicBarrier 循环栅栏
`CyclicBarrier` 是一个同步工具类，它允许一组线程互相等待，直到到达某个公共屏障点（也称为栅栏）时，再一起继续执行。
CyclicBarrier 基于计数器实现，主要特点包括：
1. 循环屏障：与 CountDownLatch 不同，CyclicBarrier 可以重复使用（"Cyclic"即循环的意思）
2. 屏障点：所有线程必须到达的同步点
3. 栅栏操作：当所有线程到达屏障时，可以执行一个可选的 Runnable 任务

工作原理：
```
线程1 →|
线程2 →|→ 所有线程到达屏障 → 执行栅栏操作(可选) → 所有线程继续执行
线程3 →|
```
主要方法：
```java
// 构造方法（指定参与线程数）
CyclicBarrier(int parties)
// 构造方法（指定参与线程数和栅栏操作）
CyclicBarrier(int parties, Runnable barrierAction)

// 等待其他线程到达
int await()
// 带超时的等待
int await(long timeout, TimeUnit unit)
// 重置屏障
void reset()
// 获取等待的线程数
int getNumberWaiting()
// 获取参与方数量
int getParties()
```
CyclicBarrier 代码示例：
基础用法：
```java
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        final int THREAD_COUNT = 3;
        CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT, 
            () -> System.out.println("所有线程已到达屏障，继续执行"));
        
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 开始执行");
                    Thread.sleep((long)(Math.random() * 3000));
                    System.out.println(Thread.currentThread().getName() + " 到达屏障");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " 继续执行后续操作");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "线程-" + i).start();
        }
    }
}

// CyclicBarrier重复用法
CyclicBarrier barrier = new CyclicBarrier(2, () -> System.out.println("屏障触发"));

// 第一次使用
new Thread(() -> { barrier.await(); }).start();
new Thread(() -> { barrier.await(); }).start();

// 可以重复使用
new Thread(() -> { barrier.await(); }).start();
new Thread(() -> { barrier.await(); }).start();
```


CyclicBarrier 与 CountDownLatch 的对比

| 特性 | CyclicBarrier | CountDownLatch |
|---------|---------------|----------------|
| 重用性 | 可重复使用 | 一次性使用 |
| 计数器方向 | 递增（达到指定值触发） | 递减（减到0触发） |
| 触发操作 | 所有线程到达后执行 Runnable | 无此功能 |
| 等待机制 | 线程主动调用 await() 等待 | 线程调用 await() 等待计数归零 |
| 重置 | 有 reset() 方法 | 无重置机制 |
| 典型应用场景 | 多阶段任务同步 | 主线程等待多个子线程完成 |

实际应用场景：
1. 多线程数据加载：多个线程分别加载数据的不同部分，全部加载完成后合并结果
2. 并行计算：将大任务分解为多个子任务，所有子任务完成后汇总结果
3. 多步骤任务：需要分多个阶段完成的任务，每个阶段需要所有线程同步

注意事项：
1. 屏障线程数：构造时指定的线程数必须与实际调用 await() 的线程数一致，否则会一直等待
2. 重置风险：reset() 操作会立即打破当前屏障，可能导致正在等待的线程收到 BrokenBarrierException
3. 异常处理：需要妥善处理 InterruptedException 和 BrokenBarrierException
4. 性能考虑：对于大量线程的同步，可能会成为性能瓶颈


## Semaphore 信号量
Semaphore 是一个同步工具类，它允许一定数量的线程同时访问某个资源。Semaphore 内部维护了一个计数器，当计数器的值为0时，表示所有等待的线程都无法访问资源。

基本概念：
Semaphore维护了一组许可证（permits）：
 - 线程访问资源前必须先获取许可证
 - 使用完资源后必须释放许可证
 - 当没有可用许可证时，获取操作会阻塞

核心特性：
1. 流量控制：限制同时访问某资源的线程数量
2. 公平性选择：支持公平和非公平模式
3. 可扩展性：许可证数量可以动态调整

公平与非公平模式：
Semaphore的公平性(fairness)是指当多个线程竞争获取许可证(permits)时，系统分配许可证的顺序策略。这一特性直接影响线程获取资源的顺序和系统吞吐量。
 - 公平模式：按照线程请求的顺序分配许可证，FIFO（先进先出）原则。 `new Semaphore(permits, true)`
 - 非公平模式：不考虑线程请求的顺序，允许插队，竞争获取。`new Semaphore(permits, false)`。在高竞争环境下表现更好，吞吐量较高。


主要方法：
```java
// 构造方法（指定许可证数量）
Semaphore(int permits)
// 构造方法（指定许可证数量和公平模式）
Semaphore(int permits, boolean fair)

// 获取许可证（阻塞直到获取或中断）
void acquire() 
// 获取指定数量的许可证  
void acquire(int permits)
// 尝试获取许可证（非阻塞）
boolean tryAcquire()
// 带超时的尝试获取
boolean tryAcquire(long timeout, TimeUnit unit)
// 释放许可证
void release()
// 释放指定数量的许可证
void release(int permits)
// 当前可用许可证数
int availablePermits()
// 正在等待获取的线程数
int getQueueLength()
```

使用示例：
```java
//限制资源并发访问
public class SemaphoreDemo {
    // 只允许3个线程同时访问
    private static final Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    semaphore.acquire();  // 获取许可
                    System.out.println(Thread.currentThread().getName() 
                        + " 获取资源，当前可用许可: " 
                        + semaphore.availablePermits());
                    // 模拟资源使用
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() 
                        + " 释放资源");
                    semaphore.release();  // 释放许可
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }
}

// 二进制信号量 类似锁，相当于一个非重入锁
Semaphore binarySemaphore = new Semaphore(1);

// 公平模式：按请求顺序分配许可证
Semaphore fairSemaphore = new Semaphore(3, true);

```
Semaphore基于AQS（AbstractQueuedSynchronizer）实现：
- state字段：表示当前可用的许可证数量
- 获取许可：将state减1，不足时线程进入等待队列
- 释放许可：将state加1，唤醒等待线程

Semaphore 与 锁的对比：
| 特性 | Semaphore | 锁 |
|---------|---------------|----------------|
| 资源数量 | 控制多个线程访问 | 控制单个线程访问 |
| 可重入性 | 默认不可重入 | 	可重入 |
| 许可证数量 | 可以设置初始数量| 固定为1 |
| 释放机制| 可由不同线程获取和释放 | 必须由获取锁的线程释放 |
| 典型应用场景 | 流量控制（限流）、资源池管理 | 资源独占 |

实际使用场景：
1. 数据库连接池：限制同时使用的连接数
2. API限流：控制对外部服务的调用频率
3. 资源池管理：如线程池、对象池等
4. 生产者消费者问题：有界缓冲区实现
5. 并行任务控制：限制同时执行的任务数



### Phase 阶段器
Phaser是Java 7引入的一个强大的同步辅助类，它比CyclicBarrier和CountDownLatch更加灵活和强大，适用于多阶段任务的同步控制。

主要方法：
```java
// 构造方法
Phaser()                      // 初始parties=0
Phaser(int parties)           // 指定初始parties数
Phaser(Phaser parent)         // 指定父Phaser
Phaser(Phaser parent, int parties)

// 注册/注销
int register()                // 注册一个新party
int arriveAndDeregister()     // 到达并注销一个party

// 到达方法
int arrive()                  // 到达但不等待
int arriveAndAwaitAdvance()   // 到达并等待其他party
int awaitAdvance(int phase)   // 等待阶段前进

// 状态控制
void forceTermination()       // 强制终止
boolean isTerminated()        // 是否已终止

// 监控方法
int getPhase()                // 获取当前阶段号
int getRegisteredParties()    // 获取注册的parties数
int getArrivedParties()       // 获取已到达的parties数
int getUnarrivedParties()     // 获取未到达的parties数
```

使用示例：
多阶段任务
```java
public class PhaserDemo {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(3); // 3个参与线程
        
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 完成阶段0");
                phaser.arriveAndAwaitAdvance(); // 等待所有线程完成阶段0
                
                System.out.println(Thread.currentThread().getName() + " 完成阶段1");
                phaser.arriveAndAwaitAdvance(); // 等待所有线程完成阶段1
                
                System.out.println(Thread.currentThread().getName() + " 完成阶段2");
                phaser.arriveAndDeregister(); // 完成并注销
            }).start();
        }
    }
}
```


## Exchanger 数据交换器
Exchanger 是 Java 并发包 (java.util.concurrent) 中一个用于线程间配对交换数据的同步点工具类。它允许两个线程在预定的交换点交换各自的数据，是一种更灵活的线程通信机制。

核心概念：
1. 配对交换：两个线程在交换点互相交换数据
2. 双向传输：不同于生产者-消费者模式，Exchanger 允许双向数据传输
3. 同步点：线程会阻塞直到配对线程也到达交换点

工作原理：
```
线程A → 携带数据A → 到达交换点 → 等待线程B → 交换数据 → 获得数据B
线程B → 携带数据B → 到达交换点 → 等待线程A → 交换数据 → 获得数据A
```

主要方法：
```java
// 构造方法
Exchanger<V>()

// 交换方法（阻塞直到另一个线程到达）
V exchange(V x) throws InterruptedException

// 带超时的交换方法
V exchange(V x, long timeout, TimeUnit unit) 
    throws InterruptedException, TimeoutException
```
使用示例：
两个线程交换数据：
```java
public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        
        new Thread(() -> {
            try {
                String data = "Data from Thread A";
                System.out.println("Thread A 发送: " + data);
                String received = exchanger.exchange(data);
                System.out.println("Thread A 收到: " + received);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        
        new Thread(() -> {
            try {
                String data = "Data from Thread B";
                System.out.println("Thread B 发送: " + data);
                String received = exchanger.exchange(data);
                System.out.println("Thread B 收到: " + received);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
```

生产者-消费者模式：
```java
Exchanger<List<String>> exchanger = new Exchanger<>();
// 生产者
new Thread(() -> {
    List<String> buffer = new ArrayList<>();
    while (true) {
        // 生产数据
        buffer.add("item-" + System.currentTimeMillis());
        if (buffer.size() >= 5) {
            try {
                System.out.println("生产者交换前: " + buffer);
                buffer = exchanger.exchange(buffer);  // 交换空的缓冲区
                System.out.println("生产者获得空缓冲区");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}).start();

// 消费者
new Thread(() -> {
    List<String> buffer = new ArrayList<>();
    while (true) {
        try {
            buffer = exchanger.exchange(buffer);  // 交换满的缓冲区
            System.out.println("消费者收到: " + buffer);
            buffer.clear();  // 清空缓冲区
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}).start();
```
核心特性：
1. 线程配对：严格一对一交换，多线程使用时随机配对。奇数线程会导致一个线程一直等待
2. 数据双向传输：双方线程都能发送和接收数据
3. 阻塞等待：线程会阻塞直到配对线程到达交换点
4. 泛型支持：可以交换任意类型对象
5. 超时机制：支持带超时的交换操作

Exchanger 提供了一种优雅的线程间双向数据交换机制，特别适合需要严格配对交换数据的场景。正确使用可以简化某些复杂的线程交互模式。

# ThradLocal
`ThreadLocal`是Java中一个特殊的线程级别的**变量存储类**，它属于线程封闭技术的一种实现，**用于实现线程间的数据隔离。**

核心特点：
1. 线程隔离：每个线程只能看到和修改自己的副本
2. 避免同步：由于数据不共享，自然不需要同步
3. 空间换时间：通过为每个线程创建副本来避免锁竞争

实现原理：
ThreadLocal内部使用了一个特殊的Map结构（ThreadLocalMap）来存储数据。
每个线程内部都有一个ThreadLocalMap，使用弱引用的Entry数组存储数据。


经典使用场景：
1. 保存线程上下文信息
```java
private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

public void processRequest(Request request) {
    currentUser.set(request.getUser());
    try {
        // 业务处理，任何地方都可以通过currentUser.get()获取当前用户
        doBusinessLogic();
    } finally {
        currentUser.remove(); // 必须清理，防止内存泄漏
    }
}
```

ThreadLocal可能引起内存泄漏，原因在于：

```
线程 (强引用) → ThreadLocalMap (强引用) → Entry (强引用) → value (强引用)
                     ↑
ThreadLocal实例 (弱引用)
```

1. Entry的key是弱引用：ThreadLocal对象被回收后，key变为null
2. value是强引用：如果线程长期存活，value无法被回收

解决方法：
- 使用完ThreadLocal后必须调用remove()方法
- 尽量将ThreadLocal声明为static final。

static可以确保每个类只有一个ThreadLocal实例，而非每个对象都有一个ThreadLocal实例，避免重复创建；final 防止 ThreadLocal 实例被意外重新赋值，导致旧实例无法被清理。ThreadLocal 的 key（弱引用）依赖于实例的稳定性。如果 ThreadLocal 变量被重新赋值，旧 key 仍可能被某些线程的 ThreadLocalMap 引用，但无法通过新 key 访问旧值，造成内存泄漏。

```java
// 非final的风险
static ThreadLocal<String> threadLocal = new ThreadLocal<>();
threadLocal = new ThreadLocal<>(); // 旧ThreadLocal泄漏！
```
关键点：ThreadLocal是怎么存储值的？
- 每个线程内部维护一个叫做 `ThreadLocalMap` 的数据结构，结构大致是 `ThreadLocalMap.Entry[]` 数组。
- 这个Map的key是ThreadLocal对象本身的**弱引用**（WeakReference），value是对应线程变量的值。
- 弱引用的含义是：只要key的ThreadLocal对象没有被强引用，就可以被GC回收。

为什么旧ThreadLocal会泄漏？
1. 线程里有 ThreadLocalMap，其中存储了旧的 ThreadLocal 的弱引用作为key，和对应的value。
2. 当你用 threadLocal = new ThreadLocal<>() 重新赋值后：
    - threadLocal 变量本身不再引用旧的 ThreadLocal 对象。
    - 但是，旧的 ThreadLocal 对象仍然作为 key 存在于每个线程的 ThreadLocalMap 里！
3. 这里的关键是：ThreadLocalMap的key是弱引用，如果旧ThreadLocal对象没有别的强引用，理论上应该被回收。
4. 但是有一个问题是，ThreadLocalMap中的 Entry 的 value（线程变量的值）是强引用，且ThreadLocalMap本身强引用存在于Thread对象中。
5. 如果某个线程一直存活（比如线程池线程），ThreadLocalMap也一直存活，旧的ThreadLocal对象的弱引用key被GC回收后，会变成null，但是Entry的value不会自动清除。
6. 结果就是：ThreadLocalMap中key为null，value却还存在，这部分值无法被访问，也无法被清除，导致内存泄漏。


### 多线程使用一个static final ThreadLocal是操作共享变量吗？还是线程隔离的吗？

多个线程使用同一个`static final ThreadLocal`实例时，**存储的值是线程隔离的**，而不是共享变量。这是ThreadLocal设计的核心特性。

核心机制

1. **存储结构**：
   - 每个线程(Thread)内部维护了一个`ThreadLocalMap`
   - 这个Map以ThreadLocal实例为key，以线程私有值为value
   - 由于key是同一个static final ThreadLocal对象，不同线程通过它访问各自独立的值

2. **数据访问流程**：
   ```java
   // static final声明
   private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
   
   // 线程A
   threadLocal.set("A的数据"); → 存入线程A的ThreadLocalMap：key=threadLocal, value="A的数据"
   
   // 线程B
   threadLocal.set("B的数据"); → 存入线程B的ThreadLocalMap：key=threadLocal, value="B的数据"
   ```


为什么能实现隔离？

1. **数据存储位置不同**：
   - 值实际存储在各线程的ThreadLocalMap中
   - 虽然key相同(都是那个static final ThreadLocal对象)，但Map属于不同线程

2. **JVM实现保证**：
   ```java
   public void set(T value) {
       Thread t = Thread.currentThread();  // 关键：获取当前线程
       ThreadLocalMap map = getMap(t);     // 获取当前线程的Map
       if (map != null) {
           map.set(this, value);  // this就是那个static final ThreadLocal
       } else {
           createMap(t, value);
       }
   }
   ```

## 示例验证

```java
public class ThreadLocalDemo {
    private static final ThreadLocal<String> TL = new ThreadLocal<>();
    
    public static void main(String[] args) {
        new Thread(() -> {
            TL.set("线程A的值");
            System.out.println("线程A读取：" + TL.get()); // 输出"线程A的值"
        }).start();
        
        new Thread(() -> {
            TL.set("线程B的值");
            System.out.println("线程B读取：" + TL.get()); // 输出"线程B的值"
        }).start();
    }
}
```

## 关键结论

1. **static final修饰的是ThreadLocal实例本身**，不是存储的值
2. **值的存储是线程隔离的**，因为每个线程有自己的ThreadLocalMap
3. 这种设计实现了：
   - ThreadLocal实例的全局唯一性（static final保证）
   - 变量值的线程隔离性（ThreadLocalMap机制保证）

因此，多个线程使用同一个static final ThreadLocal时：
- **操作的是同一个ThreadLocal对象**（因为static final）
- **访问的是各自线程隔离的值**（因为值存在各自线程的Map中）

这正是ThreadLocal的精妙之处：通过统一的访问入口（static final实例），实现数据存储的线程隔离。


# CompletableFuture

`CompletableFuture` 是 Java 8 引入的一个强大的`异步编程工具`，它实现了 **Future** 和 **CompletionStage** 接口，提供了更灵活、更强大的异步编程能力。

核心特性：
1. 异步执行：可以轻松地异步执行任务
2. 链式调用：支持将多个异步操作串联起来
3. 组合操作：可以组合多个 Future
4. 异常处理：提供了完善的异常处理机制
5. 手动完成：可以手动设置完成状态和结果

## 创建 CompletableFuture

使用静态工厂方法
```java
// 创建一个已完成的Future
CompletableFuture<String> completedFuture = CompletableFuture.completedFuture("Hello");

// 异步执行任务（使用默认的ForkJoinPool）
CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
    System.out.println("异步执行无返回值的任务");
});

// 异步执行有返回值的任务
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    return "异步执行结果";
});

```

## 基本操作

获取结果
```java
// 阻塞获取结果
String result = future.get();
// 带超时的获取
String result = future.get(1, TimeUnit.SECONDS);
// 获取或返回默认值
String result = future.getNow("默认值");


```

完成状态检查
```java
future.isDone();     // 是否完成（正常完成或异常完成）
future.isCancelled(); // 是否被取消
future.isCompletedExceptionally(); // 是否异常完成
```

## 链式操作
1. thenApply - 转换结果
    ```java
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
        .thenApply(s -> s + " World")
        .thenApply(String::toUpperCase);

    System.out.println(future.get()); // 输出 "HELLO WORLD"
    ```
2. thenAccept - 消费结果
    ```java
    CompletableFuture.supplyAsync(() -> "Hello")
        .thenAccept(s -> System.out.println("结果是: " + s));
    ```
3. thenRun - 执行后续操作
    ```java
    CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> "Hello")
        .thenRun(() -> System.out.println("World"));
    ```

## 组合多个 Future
1. thenCompose - 顺序组合
    ```java
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
       .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
    ```
2. thenCombine - 并行组合
    ```java
    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
      .thenCombine(CompletableFuture.supplyAsync(() -> " World"), (s1, s2) -> s1 + s2);
    ```
3. allOf / anyOf - 多Future组合
    ```java
    CompletableFuture<String> future1 = ...;
    CompletableFuture<String> future2 = ...;
    CompletableFuture<String> future3 = ...;

    // 等待所有完成
    CompletableFuture<Void> all = CompletableFuture.allOf(future1, future2, future3);

    // 等待任意一个完成
    CompletableFuture<Object> any = CompletableFuture.anyOf(future1, future2, future3);
    ```
## 异常处理

1. exceptionally - 异常处理
    ```java
    CompletableFuture.supplyAsync(() -> {
        if (true) throw new RuntimeException("出错啦");
        return "Hello";
    }).exceptionally(ex -> {
        System.out.println("异常: " + ex.getMessage());
        return "默认值";
    });
    ```
2. handle - 异常处理和结果处理  
    ```java         
    CompletableFuture.supplyAsync(() -> {
        if (true) throw new RuntimeException("出错啦");
        return "Hello";
    }).handle((res, ex) -> {
        if (ex != null) {
            return "异常处理后的默认值";
        }
        return res;
    });
    ```
## 线程池控制
默认使用 **ForkJoinPool.commonPool()**，可以指定自定义线程池：
```java
ExecutorService executor = Executors.newFixedThreadPool(10);

CompletableFuture.supplyAsync(() -> {
    // 任务逻辑
    return "结果";
}, executor);
```

## 高级用法
1. orTimeout 超时控制
    ```java
    CompletableFuture.supplyAsync(() -> {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "结果";
    }).orTimeout(1, TimeUnit.SECONDS)  // 设置超时
    .exceptionally(ex -> "超时默认值");
    ```
2. thenApplyAsync 异步回调
    ```java 
    future.thenApplyAsync(s -> s.toUpperCase())
        .thenAcceptAsync(System.out::println);
    ```
3. whenComplete 完成时回调
    ```java
    future.whenComplete((result, ex) -> {
        if (ex != null) {
            System.out.println("异常: " + ex.getMessage());
        } else {
            System.out.println("结果: " + result);
        }
    });
    ```


## 实际应用场景
1. 异步API调用：并行调用多个外部服务
2. 流水线处理：将多个异步操作串联
3. 批量任务处理：处理一批任务并收集结果

---

CompletableFuture 提供了比传统 Future 更强大的异步编程能力，通过灵活的组合操作，可以构建复杂的异步工作流，是 Java 异步编程的重要工具。

# 并发集合
1. CopyOnWriteArrayList: 读操作无锁，写操作复制新数组
2. ConcurrentHashMap: JDK8前：分段锁; JDK8及以后：CAS + synchronized
3. ConcurrentLinkedQueue: 无界非阻塞队列，适用于高并发场景
4. BlockingQueue实现类:
    - ArrayBlockingQueue: 基于数组实现的有界阻塞队列
    - LinkedBlockingQueue: 基于链表实现的可选有界阻塞队列
    - PriorityBlockingQueue: 基于优先级堆实现的无界阻塞队列
    - DelayQueue: 基于优先级堆实现的无界阻塞延迟队列
    - SynchronousQueue: 不存储元素的阻塞队列

# 场景题
## 1. 实现消费者生产者模型
## 2. 两个线程交替打印奇偶数
## 3. 实现一个阻塞队列
