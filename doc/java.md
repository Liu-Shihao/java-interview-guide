
# Java 基础

## Byte（字节）& bit（位）
一个字节等于八位，即 1Byte = 8bit
1. Byte（字节）：计算机中最小的存储单位，通常由 8 个 bit（位）组成。
2. bit（位）：计算机中最小的数据单位，用于表示二进制数中的一位。它只能表示 0 或 1 这两种状态；

为了更好地理解它们之间的换算，下面举几个例子：

如果有一个文件大小为 1MB（兆字节），因为 1MB = 1024KB，1KB = 1024B（字节），所以 1MB = 1024×1024B = 1048576B；又因为 1B = 8bit，所以这个文件的大小换算成位就是 1048576×8 = 8388608bit。

若某网络连接的带宽是 10Mbps（Bits per Second），那么它每秒实际能传输的数据量为 10×1024×1024÷8 = 1310720Byte（字节）。

在描述网络带宽时，我们常说的1M带宽实际指的是1Mbps（兆比特每秒），而不是1MB（字节）。

## 基本数据类型
Java有8种基本数据类型，可分为四类
1. 整数类型（Integer Types）：包括 byte、short、int、long。
2. 浮点类型（Floating-Point Types）：包括 float、double。
3. 字符类型（Character Type）：char。
4. 布尔类型（Boolean Type）：boolean。

| 数据类型分类 | 具体数据类型 | 关键字 | 字节大小 | 取值范围 | 包装类 |
| --- | --- | --- | --- | --- | --- |
| 整数类型 | 字节型 | byte | 1字节 | -128 ~ 127 | java.lang.Byte |
| 整数类型 | 短整型 | short | 2字节 | -32768 ~ 32767 | java.lang.Short |
| 整数类型 | 整型 | int | 4字节 | -2^31 ~ 2^31 - 1 | java.lang.Integer |
| 整数类型 | 长整型 | long | 8字节 | -2^63 ~ 2^63 - 1 | java.lang.Long |
| 浮点类型 | 单精度浮点型 | float | 4字节 | 3.4e - 45 ~ 3.4e38 | java.lang.Float |
| 浮点类型 | 双精度浮点型 | double | 8字节 | 1.7e - 308 ~ 1.7e308 | java.lang.Double |
| 字符类型 | 字符型 | char | 2字节 | 0 ~ 65535 | java.lang.Character |
| 布尔类型 | 布尔型 | boolean | 1字节 | true、false | java.lang.Boolean |

不同数据类型的取值范围计算方式不同，下面以Java的基本数据类型为例进行说明：

### 整数类型
整数类型在计算机中以二进制形式存储，其取值范围由存储位数和编码方式决定，通常采用二进制补码表示有符号整数。对于有符号整数，最高位为符号位（0 表示正数，1 表示负数），其余位表示数值大小。
- **byte**：在内存中占 8 位（1 字节）。由于最高位为符号位，所以剩下 7 位用于表示数值。因此，其取值范围是$-2^7$ 到 $2^7 - 1$，即 -128 到 127。
- **short**：占 16 位（2 字节）。最高位为符号位，剩下 15 位用于表示数值，取值范围是$-2^{15}$ 到 $2^{15} - 1$，也就是 -32768 到 32767。
- **int**：占 32 位（4 字节）。最高位为符号位，其余 31 位表示数值，取值范围是$-2^{31}$ 到 $2^{31} - 1$，即 -2147483648 到 2147483647。
- **long**：占 64 位（8 字节）。最高位为符号位，其余 63 位表示数值，取值范围是$-2^{63}$ 到 $2^{63} - 1$，即 -9223372036854775808 到 9223372036854775807。

在计算机中，有符号整数常采用补码表示法，其中 -1 有着特殊的二进制表示，下面结合原码、反码的概念以及运算优势等方面，通过示例为你详细解释：

### 原码、反码、补码的概念
- **原码**：最高位为符号位，0 表示正数，1 表示负数，其余位表示数值的绝对值。例如，对于 8 位二进制数，+1 的原码是 0000 0001， -1 的原码是 1000 0001。
- **反码**：正数的反码与原码相同；负数的反码是在原码的基础上，符号位保持不变，其余位按位取反（0 变 1，1 变 0）。例如，8 位二进制数 -1 的反码是 1111 1110。
- **补码**：正数的补码与原码相同；负数的补码是在其反码的基础上末尾加 1。例如，8 位二进制数 -1 的补码是 1111 1111。

### 为什么要用补码表示 -1 及其优势
#### 1. 解决原码和反码的问题
- **原码的问题**：原码在进行减法运算时会导致硬件设计复杂，因为需要额外处理符号位。例如，使用原码进行 1 - 1 的运算，计算机需要判断两个数的符号，然后根据符号执行不同的操作，这增加了硬件的复杂性和运算时间。若用原码计算 1 - 1，1 的原码是 0000 0001， -1 的原码是 1000 0001，直接相加得到 1000 0010，这是 -2 的原码，结果错误。
- **反码的问题**：反码虽然比原码更接近补码，运算时相对简单一些，但仍然存在两个零（+0 和 -0），导致额外的复杂性；加法运算需要处理溢出位，对于负数加法，有时需要额外的修正步骤，增加了计算复杂度。例如，8 位二进制数 -1 的反码是 1111 1110，若进行加法运算时出现进位，还需要额外处理。
- **补码的优势**：补码通过将减法运算转换为加法运算，简化了硬件设计。在补码表示法中，正数的补码与原码相同，负数的补码是其原码取反加 1。这样计算机在执行加减法运算时，只需进行加法操作，无需额外处理符号位。例如，计算 1 + (-1)，1 的补码是 0000 0001， -1 的补码是 1111 1111，两者相加 0000 0001 + 1111 1111 = 0000 0000（进位自然丢失），得到了正确的结果 0，且没有 +0 和 -0 的区分问题。

#### 2. 统一的零表示
原码和反码中存在正负 0 的区分，而补码则确保了 0 的唯一表示。在 8 位二进制中，+0 的原码、反码、补码都是 0000 0000， -0 的原码是 1000 0000，反码是 1111 1111，补码是 0000 0000（进位后）。这样就简化了零的处理。

#### 3. 高效的溢出检测
补码表示使得溢出检测更加直观和统一，通过检查最高位（符号位）的变化可以判断是否发生了溢出。例如，在 8 位二进制补码运算中，如果两个正数相加结果为负数，或者两个负数相加结果为正数，就说明发生了溢出。

#### 4. 范围更高效利用
对于 n 位整数，补码表示的范围是 $-2^{n - 1}$ 到 $2^{n - 1} - 1$，比原码和反码多表示一个负数。例如，8 位二进制原码和反码能表示的范围是 -127 到 +127，而 8 位二进制补码能表示的范围是 -128 到 +127。

综上所述，计算机使用补码表示 -1 以及其他负数，是为了简化硬件设计、提高计算效率、统一零的表示以及更高效地利用数值表示范围。


整数的取值范围最大值减1主要是由整数的表示方式和计算机中整数运算的规则决定的，下面为你详细解释：

### 1. 二进制表示与位数限制
在计算机中，整数通常以二进制形式存储，其取值范围受限于存储位数。以n位二进制数为例，若采用补码表示有符号整数，最高位为符号位（0表示正，1表示负），剩下的n - 1位用于表示数值。对于有符号整数，其最大值为$2^{n - 1}-1$ 。以32位有符号整数为例，其最大值是$2^{31}-1$（即2147483647）。这是因为如果最大值再增加1，就会导致溢出。

### 2. 整数溢出机制
当整数运算的结果超出了该数据类型所能表示的范围时，就会发生整数溢出。在补码表示中，当最大值加1时，会发生“环绕”现象，结果会变成最小值。例如，在32位有符号整数中，最大值$2^{31}-1$加1后，结果会变成$-2^{31}$ 。这是因为在二进制补码运算中，加法是按位进行的，当最高位产生进位时，会舍弃该进位，从而导致数值从最大值“溢出”到最小值。以4位二进制补码为例，其表示范围是$-2^{3}$ 到 $2^{3}-1$，即 -8 到 7。当数值达到最大值7（二进制表示为0111）时，再加1，结果变为1000，而1000在补码表示中是最小值 -8。

### 3. 避免数值不连续和特殊处理
如果不采用最大值减1的方式，会导致数值表示不连续，出现一些特殊的数值（如原码中的 -0），这会给计算机的运算和处理带来额外的复杂性。采用补码表示法，将最大值减1，能够保证数值的连续性和运算的一致性，使得计算机可以更高效地进行数值计算。

综上所述，整数取值范围最大值减1是为了适应计算机的二进制表示和运算规则，避免溢出问题，以及确保数值表示的连续性和运算的高效性。




# 面向对编程

1. 面向对象概念
2. 类与对象
3. 封装
4. 继承
5. 多态
6. 方法
7. 抽象类与接口



# Java集合


## 集合的分类
Java中的集合类可以分为两大类：一类是实现Collection接口；另一类是实现Map接口。

Collection是一个基本的集合接口，Collection中可以容纳一组集合元素。

Map没有继承Collection接口，与Collection是并列关系，

Map提供键（key）到值（value）的映射，一个Map中不能包含相同的键，每个键只能映射一个值。

## 常见接口及实现类

### Collection接口及其子接口和实现类
- List接口：扩展了Collection接口，存储一个序列的元素（列表基于0的索引），可以包含重复的元素，但不能有null值。它提供了基于索引的访问方式，可以方便地插入、删除和访问指定位置的元素。常见的List实现类有：

    - ArrayList：基于动态数组实现，随机访问元素效率高（时间复杂度为O(1)），但在插入和删除元素时可能需要移动大量元素，效率相对较低。适用于频繁访问元素、较少修改元素的场景。

    - LinkedList：基于双向链表实现，插入和删除元素效率高（时间复杂度为O(1)），但随机访问元素需要遍历链表，效率较低。适合频繁插入和删除元素的场景。

    - Vector：是一个动态数组，它可以在运行时自动调整大小。它类似于数组，但它的大小是可变的，而不是固定的。Vector是线程安全的，但性能相对较低，在多线程环境下，可以通过synchronized关键字来保证Vector中的操作是原子性的。在Java 2中，Vector的设计被改进，很多方法改为使用Iterator进行遍历，而不再使用Enumeration。

- Set接口：扩展了Collection接口，该集合不允许存在相同的元素（包括唯一null值）。常见的Set实现类有：

    - HashSet：基于哈希表实现，元素无序，添加、删除和查找元素的效率都很高（平均时间复杂度为O(1)）。但它不保证元素的顺序，也不支持元素的排序。

    - TreeSet：基于红黑树实现，元素有序，可以按照自然顺序或指定的比较器顺序进行排序。它在添加、删除和查找元素时的效率相对较低（时间复杂度为O(log n)），但在需要有序集合时非常有用。

    - LinkedHashSet：继承自HashSet，保留元素的插入顺序。它内部使用链表维护插入顺序，因此保证了元素的迭代顺序与插入顺序一致。适用于需要保持插入顺序并且不允许重复元素存在的场景。

### Map接口及其实现类
- HashMap：基于哈希表实现，键值对无序，添加、删除和查找元素的效率都很高（平均时间复杂度为O(1)）。但它不保证键值对的顺序，也不支持键值对的排序。

- TreeMap：基于红黑树实现，键值对有序，可以按照键的自然顺序或指定的比较器顺序进行排序。它在添加、删除和查找元素时的效率相对较低（时间复杂度为O(log n)），但在需要有序键值对时非常有用。

- Hashtable：一个早期的实现了Map接口的类，它类似于HashMap，但是它的所有方法都是同步的（synchronized）。这使得Hashtable在多线程环境中是线程安全的，然而，由于其所有方法都进行同步处理，可能会导致性能上的一些损失。它主要用于需要线程安全的场景。

- LinkedHashMap：继承自HashMap，但保留了元素的插入顺序。它通过链表维护插入顺序，因此保证了元素的迭代顺序与插入顺序一致。这使得LinkedHashMap在需要按照插入顺序迭代元素时非常有用。

- Properties：继承自HashTable，用于处理属性文件。它被广泛用于读取和写入属性文件中的键值对数据，通常用于存储配置文件或简单的键值对信息。




以下是Java中主要集合类的对比表格，按照接口分类展示：

## Collection接口体系

| 集合类 | 实现接口 | 顺序性 | 唯一性 | 线程安全 | 底层实现 | 扩容机制 | 允许null | 特点 |
|--------|----------|--------|--------|----------|----------|----------|----------|------|
| ArrayList | List | 有序 | 允许重复 | 不安全 | 动态数组 | 默认10，扩容1.5倍 | 允许 | 随机访问快，增删慢 |
| LinkedList | List, Deque | 有序 | 允许重复 | 不安全 | 双向链表 | 无需扩容 | 允许 | 增删快，随机访问慢 |
| Vector | List | 有序 | 允许重复 | 安全(synchronized) | 动态数组 | 默认10，扩容2倍 | 允许 | 线程安全但性能差 |
| Stack | List(Vector子类) | 有序 | 允许重复 | 安全 | 动态数组 | 同Vector | 允许 | LIFO结构 |
| HashSet | Set | 无序 | 唯一 | 不安全 | HashMap | 默认16，负载因子0.75 | 允许1个 | 快速查找，无序 |
| LinkedHashSet | Set | 插入顺序 | 唯一 | 不安全 | LinkedHashMap | 同HashSet | 允许1个 | 保持插入顺序 |
| TreeSet | NavigableSet | 排序 | 唯一 | 不安全 | TreeMap | 无需扩容 | 不允许 | 自然排序或Comparator |
| PriorityQueue | Queue | 堆顺序 | 允许重复 | 不安全 | 二叉堆(数组) | 默认11，扩容策略复杂 | 不允许 | 优先级队列 |

## Map接口体系

| 集合类 | 实现接口 | 顺序性 | 键唯一性 | 线程安全 | 底层实现 | 扩容机制 | 允许null键值 | 特点 |
|--------|----------|--------|----------|----------|----------|----------|--------------|------|
| HashMap | Map | 无序 | 唯一 | 不安全 | 数组+链表/红黑树(JDK8+) | 默认16，负载因子0.75 | 允许1个null键和多个null值 | 高效查找，非线程安全 |
| LinkedHashMap | Map | 插入顺序/访问顺序 | 唯一 | 不安全 | 哈希表+双向链表 | 同HashMap | 同HashMap | 保持插入/访问顺序 |
| Hashtable | Map | 无序 | 唯一 | 安全(synchronized) | 数组+链表 | 默认11，负载因子0.75 | 不允许 | 线程安全但性能差 |
| TreeMap | NavigableMap | 键排序 | 唯一 | 不安全 | 红黑树 | 无需扩容 | 不允许null键 | 自然排序或Comparator |
| ConcurrentHashMap | ConcurrentMap | 无序 | 唯一 | 安全(分段锁/CAS) | 数组+链表/红黑树 | 动态扩容 | 不允许 | 高并发性能好 |
| WeakHashMap | Map | 无序 | 唯一 | 不安全 | 哈希表 | 同HashMap | 允许 | 弱引用键，适合缓存 |

## 并发集合对比

| 集合类 | 对应非线程安全类 | 线程安全机制 | 性能 | 适用场景 |
|--------|------------------|--------------|------|----------|
| Vector | ArrayList | synchronized方法 | 差 | 已过时，不推荐使用 |
| Hashtable | HashMap | synchronized方法 | 差 | 已过时，不推荐使用 |
| ConcurrentHashMap | HashMap | 分段锁+CAS(JDK7)/synchronized+CAS(JDK8+) | 高 | 高并发场景首选 |
| CopyOnWriteArrayList | ArrayList | 写时复制 | 读高写低 | 读多写少场景 |
| CopyOnWriteArraySet | HashSet | 写时复制 | 读高写低 | 读多写少场景 |
| ConcurrentSkipListMap | TreeMap | 跳表结构 | 中等 | 需要排序的并发Map |
| ConcurrentSkipListSet | TreeSet | 跳表结构 | 中等 | 需要排序的并发Set |
| ConcurrentLinkedQueue | LinkedList | CAS操作 | 高 | 高并发队列 |
| BlockingQueue接口实现类 | - | 锁+条件变量 | 中等 | 生产者-消费者模式 |

## 选择指南

1. **需要唯一性**：
   - Set接口实现类(HashSet, LinkedHashSet, TreeSet)
   
2. **需要键值对**：
   - Map接口实现类(HashMap, LinkedHashMap, TreeMap)

3. **需要线程安全**：
   - 并发包下的集合(ConcurrentHashMap, CopyOnWriteArrayList等)
   
4. **需要排序**：
   - TreeSet, TreeMap, ConcurrentSkipListSet, ConcurrentSkipListMap

5. **需要保持插入顺序**：
   - LinkedHashSet, LinkedHashMap, ArrayList, LinkedList

6. **需要高性能随机访问**：
   - ArrayList

7. **需要频繁增删**：
   - LinkedList

8. **高并发环境**：
   - ConcurrentHashMap > Collections.synchronizedMap > Hashtable



# 多线程与并发

- 进程：操作系统资源分配的基本单位，拥有独立的内存空间。
- 线程：线程是程序执行的最小单元，CPU调度的基本单位，共享进程资源，轻量级执行单元
- ​​多线程​​：指在同一个进程中，同时存在两个或多个执行的线程，各个线程可以同时执行（在多核处理器上）或交替执行（在单核处理器上通过时间片轮转），从而实现程序的并发执行。
- 并发​​：指多个任务交替执行，从宏观上看，任务似乎是同时进行的。并发编程允许程序在同一时间内处理多个任务，提高程序的效率和响应性。
- 并行​​：指多个任务同时执行，从微观上看，任务是真正同时进行的。并行编程利用多核处理器的优势，提高程序的执行效率。


## Java线程实现方式

```java
// 方式1：继承Thread类
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

// 方式2：实现Runnable接口
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("线程正在运行。");
    }
    
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start(); // 启动线程
    }
}

// 方式3：使用Lambda表达式
public class LambdaThreadExample {
    public static void main(String[] args) {
        Runnable task = () -> System.out.println("线程正在运行。");
        Thread thread = new Thread(task);
        thread.start();
    }
}


// 方式4：实现Callable接口（带返回值）
class CallableTest implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0) sum += i;
        }
        return sum;
    }
    
    public static void main(String[] args) throws Exception {
        CallableTest ct = new CallableTest();
        FutureTask<Integer> ft = new FutureTask<>(ct);
        new Thread(ft).start();
        System.out.println("总和为：" + ft.get());
    }
}
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

## 线程的状态

Java线程在其生命周期中有以下几种状态：

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



## 线程池

线程池是一种管理和复用线程的机制，它可以有效地控制线程的数量，避免线程频繁创建和销毁的开销，提高系统性能和响应速度。
### 线程池的核心参数
线程池的核心参数包括：
1. **核心线程数(Core Pool Size)**：线程池中保持的线程数，即使这些线程处于空闲状态，也不会被销毁。
2. **最大线程数(Maximum Pool Size)**：线程池中允许的最大线程数，当核心线程数已满且任务队列已满时，线程池会创建新的线程，直到达到最大线程数。
3. **空闲线程存活时间(Keep Alive Time)**：当线程数超过核心线程数时，多余的空闲线程在终止前等待新任务的最长时间。
4. **任务队列(Work Queue)**：用于存放待执行任务的队列，当核心线程数已满且任务队列已满时，新的任务会被拒绝。
5. **线程工厂(Thread Factory)**：用于创建新线程的工厂，通常用于设置线程的优先级、名称等属性。
6. **拒绝策略(Rejection Policy)**：当线程池和任务队列都满时，新任务的处理策略，如直接拒绝、丢弃旧任务、丢弃新任务等。

## 线程安全

线程安全是指在多线程环境下，多个线程同时访问共享资源时不会出现数据不一致或其他异常情况的特性。

Java线程安全问题主要来源于：

1. 共享数据的并发访问：多个线程同时读写同一数据
2. 操作的非原子性：看似单一的操作可能由多个步骤组成
3. 内存可见性问题：一个线程的修改对其他线程不可见
4. 指令重排序：编译器和处理器可能改变代码执行顺序


## Java内存模型(JMM)
Java内存模型(Java Memory Model，JMM)是Java虚拟机规范中定义的一种抽象模型，用于描述Java程序在多线程环境下的内存访问行为。
JMM的主要目标是定义一组规则，确保在多线程环境下，线程之间的共享变量的读写操作的`可见性`、`原子性`和`有序性`。
- 原子性：操作要么完全执行，要么完全不执行
- 可见性：一个线程对共享变量的修改对其他线程立即可见
- 有序性：程序执行的顺序按照代码的先后顺序执行

JMM的核心概念包括：
1. **主内存**：所有线程共享的内存区域，包含共享变量。
2. **工作内存**：每个线程私有的内存区域，包含线程本地变量和方法参数。
3. **内存屏障**：用于控制读写操作的顺序，确保指令的执行顺序。
4. **原子操作**：不可分割的操作，保证线程安全。


## 线程同步机制
线程同步是确保多个线程在访问共享资源时不会发生冲突的机制。Java中常用的线程同步机制包括：

1. **synchronized关键字**：用于方法或代码块，确保同一时间只有一个线程可以执行被修饰的方法或代码块。
2. **Lock接口及其实现类**：提供了显式的锁机制，包括ReentrantLock、ReadWriteLock等。
3. **volatile关键字**：用于修饰变量，确保变量的可见性和有序性。
4. **原子类(AtomicXXX)**：提供了线程安全的基本数据类型操作，如AtomicInteger、AtomicLong等。
5. **并发集合类**：如ConcurrentHashMap、CopyOnWriteArrayList等，提供了线程安全的集合操作。

### synchronized关键字
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
第一个访问的线程通过CAS将Mark Word中的线程ID替换为自己的ID
之后该线程进入同步块只需检查线程ID是否匹配
匹配则直接执行，不匹配说明有竞争，升级为轻量级锁  
3. **轻量级锁**：当有多个线程同时获取锁时，偏向锁会升级为轻量级锁。轻量级锁使用CAS操作来尝试获取锁，如果成功则将对象的`Mark Word`中的线程ID设置为当前线程的ID，同时将对象的状态设置为轻量级锁（00）。失败则自旋尝试，自旋超过阈值或第三个线程来竞争，升级为重量级锁。
4. **重量级锁**：当有多个线程同时获取锁时，轻量级锁会升级为重量级锁（10）。重量级锁使用操作系统的互斥量来实现，当一个线程获取锁时，会阻塞其他线程的访问。    
实现原理：
对象关联一个监视器(Monitor)对象
未获取锁的线程进入阻塞队列
依赖操作系统的互斥量(mutex)实现
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


## JUC（java.util.concurrent）同步工具

### Lock接口及其实现类

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




### volatile关键字

`volatile`关键字用于修饰变量，确保变量的可见性和有序性。当一个变量被声明为`volatile`时，每次读取该变量时都会从主内存中获取最新的值，而每次写入该变量时都会立即刷新到主内存中。
volatile 是 Java 中用于保证变量可见性和禁止指令重排序的关键字，它是 Java 轻量级的同步机制之一。
核心特性：
1. **可见性**：当一个变量被声明为 volatile 时，对该变量的修改会立即刷新到主内存中，而对该变量的读取也会从主内存中获取最新的值。
2. **有序性**：volatile 关键字禁止了指令重排序，保证了代码的执行顺序。

使用 volatile 关键字的场景：
1. **状态标志**：当一个变量的值可能被多个线程修改时，可以使用 volatile 来确保变量的可见性。
2. **双重检查锁定**：在单例模式中，使用 volatile 可以确保双重检查锁定的正确性。
3. **计数器**：当一个变量的值需要被多个线程累加时，可以使用 volatile 来确保变量的可见性。
4. **标志位**：当一个变量的值需要被多个线程修改时，可以使用 volatile 来确保变量的可见性。



#### 核心特性

#### 1. 可见性保证 (Visibility)
- 当一个线程修改了 `volatile` 变量的值，新值会立即被刷新到主内存中
- 当其他线程读取该变量时，会直接从主内存中读取最新值，而不是使用线程本地缓存(工作内存)

#### 2. 禁止指令重排序 (Ordering)
- JVM 和 CPU 会对指令进行优化重排，但 `volatile` 会禁止这种重排序
- 通过插入内存屏障(Memory Barrier)实现

### 内存语义

#### 写操作语义
1. 修改线程工作内存中的变量副本
2. 将修改后的值立即刷新到主内存

#### 读操作语义
1. 从主内存中读取变量最新值
2. 存入当前线程的工作内存

#### 典型使用场景

#### 1. 状态标志
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

#### 2. 单例模式的双重检查锁定(DCL)
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

### Volatile 与 synchronized 的区别

| 特性                | volatile                     | synchronized                 |
|---------------------|------------------------------|------------------------------|
| 原子性              | 仅保证单次读/写的原子性       | 保证代码块/方法的原子性        |
| 可见性              | 保证                          | 保证                          |
| 互斥性              | 不保证                        | 保证                          |
| 性能                | 更高                          | 相对较低                      |
| 阻塞                | 不会导致线程阻塞              | 可能导致线程阻塞              |
| 使用场景            | 状态标志、双重检查锁定等       | 需要原子性复合操作的场景       |

### Volatile局限性

注意：
1. volatile 不保证原子性，仅保证可见性和有序性。
   ```java
   volatile int count = 0;
   count++; // 这不是原子操作，实际是读-改-写三个操作
   ```
2. volatile 不能保证线程安全，需要结合其他同步机制使用。
3. volatile 适用于简单的变量，不适用于复杂的操作。


### Volatile底层实现原理

#### 内存屏障
JVM 会在 `volatile` 变量操作前后插入内存屏障：

1. **写操作前**：StoreStore 屏障
2. **写操作后**：StoreLoad 屏障
3. **读操作前**：LoadLoad 屏障 + LoadStore 屏障

#### 缓存一致性协议
现代 CPU 通常使用 MESI 协议来保证缓存一致性，`volatile` 的实现也依赖于此

### Volatile最佳实践

1. 仅当变量真正独立于程序其他状态时使用
2. 避免过度使用，因为内存屏障会影响性能
3. 对于简单的状态标志，`volatile` 是比锁更好的选择
4. 在复杂的同步场景中，考虑使用 `java.util.concurrent.atomic` 包中的原子类

`volatile` 提供了一种比锁更轻量级的线程间通信机制，但正确使用需要深入理解其语义和限制。
   





## Java 原子类 (Atomic Classes)

Java 原子类是 `java.util.concurrent.atomic` 包下的一组类，它们提供了一种线程安全的方式来操作单个变量，无需使用同步锁（如 `synchronized`）。

原子类(AtomicXXX)提供了线程安全的基本数据类型操作，如`AtomicInteger`、`AtomicLong`等。这些类的方法都是原子操作，即执行过程中不会被其他线程打断。

## 核心特点

1. **无锁线程安全**：基于 `CAS` (Compare-And-Swap) 实现，而非传统锁机制
2. **高性能**：避免了线程阻塞和上下文切换的开销
3. **原子性保证**：单个操作是原子的
4. **内存可见性**：保证变量的修改对其他线程立即可见

## 主要原子类

### 1. 基本类型原子类
- `AtomicBoolean`：原子更新布尔类型
- `AtomicInteger`：原子更新整型
- `AtomicLong`：原子更新长整型

### 2. 引用类型原子类
- `AtomicReference`：原子更新引用类型
- `AtomicStampedReference`：带版本号的原子引用（解决ABA问题）
- `AtomicMarkableReference`：带标记位的原子引用

### 3. 数组原子类
- `AtomicIntegerArray`：原子更新整型数组元素
- `AtomicLongArray`：原子更新长整型数组元素
- `AtomicReferenceArray`：原子更新引用类型数组元素

### 4. 字段更新原子类
- `AtomicIntegerFieldUpdater`：原子更新对象的整型字段
- `AtomicLongFieldUpdater`：原子更新对象的长整型字段
- `AtomicReferenceFieldUpdater`：原子更新对象的引用字段

#### 核心方法

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

### 原子类实现原理 - CAS

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

现代CPU都提供了CAS指令（如x86的`CMPXCHG`），JVM会将这些操作映射为本地硬件指令。

#### 优势与局限

**优势**：
- 比锁性能更高（无阻塞）
- 避免死锁风险
- 细粒度控制（变量级别）

**局限**：
- 仅适用于单个变量
- 不适用于复杂复合操作
- ABA问题（可通过带版本号的原子类解决）
- 高竞争环境下性能可能下降（大量CAS失败）

#### 最佳实践

1. 优先使用原子类而非 `volatile` + 同步的组合
2. 对于简单计数器、状态标志等场景非常适用
3. 高竞争环境下考虑使用 `LongAdder` (Java8+) 替代 `AtomicLong`
4. 复杂操作仍需使用锁或其他同步机制

Java原子类为并发编程提供了高效、轻量级的线程安全解决方案，是构建高性能并发系统的重要工具。

### 并发集合类
并发集合类提供了线程安全的集合操作，如`ConcurrentHashMap`、`CopyOnWriteArrayList`等。这些类的方法都是原子操作，保证了线程安全。


### CountDownLatch
CountDownLatch 是一个同步工具类，它允许一个或多个线程等待其他线程完成操作后再继续执行。CountDownLatch 内部维护了一个计数器，当计数器的值为0时，表示所有等待的线程都已经完成操作，可以继续执行。

`CountDownLatch` 是 Java 并发包 (`java.util.concurrent`) 中一个非常有用的同步辅助类，它允许一个或多个线程等待其他线程完成操作后再继续执行。

#### CountDownLatch核心概念

CountDownLatch 基于计数器实现，主要特点包括：
- **初始化计数器**：构造时指定计数值
- **等待机制**：调用 `await()` 的线程会阻塞，直到计数器减到0
- **计数递减**：其他线程完成任务后调用 `countDown()` 减少计数
- **一次性使用**：计数器无法重置，用完即废弃

#### 主要方法

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


#### 使用注意事项

1. **一次性使用**：计数器减到0后无法重置，如需重复使用考虑 `CyclicBarrier`
2. **异常处理**：确保在 `finally` 块中调用 `countDown()`，避免线程异常导致计数器无法减少
3. **避免死锁**：确保计数器最终能减到0，否则等待线程会一直阻塞
4. **性能考虑**：对于极高并发场景，`countDown()` 调用不会阻塞，性能较好

#### 与相关类的比较

| 特性                | CountDownLatch             | CyclicBarrier              | Phaser                     |
|---------------------|---------------------------|---------------------------|---------------------------|
| 重用性              | 一次性                     | 可重复使用                | 可重复使用                |
| 计数器方向          | 递减                       | 递增                      | 灵活                      |
| 参与方              | 固定                       | 固定                      | 动态可变                  |
| 回调                | 无                         | 有                        | 有                        |
| 适用场景            | 主等子/子等主/混合等待      | 多线程相互等待            | 复杂分阶段任务            |

#### 实际应用场景

1. 并行任务拆分后的汇总
2. 服务启动时等待所有组件初始化完成
3. 测试并发代码时协调多个线程同时开始
4. 批量处理任务时等待所有任务完成

`CountDownLatch` 是 Java 并发编程中的重要工具类，合理使用可以简化很多复杂的线程协调问题。


### CyclicBarrier
CyclicBarrier 是一个同步工具类，它允许一组线程互相等待，直到所有线程都达到某个状态后再继续执行。CyclicBarrier 内部维护了一个计数器，当计数器的值为0时，表示所有等待的线程都已经到达屏障，可以继续执行。
### Semaphore
Semaphore 是一个同步工具类，它允许一定数量的线程同时访问某个资源。Semaphore 内部维护了一个计数器，当计数器的值为0时，表示所有等待的线程都无法访问资源。
### Phase
Phase 是一个同步工具类，它允许一组线程在不同的阶段执行不同的操作。Phase 内部维护了一个计数器，当计数器的值为0时，表示所有等待的线程都已经完成当前阶段的操作，可以进入下一个阶段。




## 线程间通信
线程间通信是指多个线程之间通过某种机制进行数据交换和协作的过程。在Java中，线程间通信主要通过以下机制实现：
1. **wait()和notify()方法**：这两个方法是Object类的一部分，用于线程间的等待和唤醒。当线程调用`wait()`方法时，它会释放持有的锁并进入等待状态，直到其他线程调用`notify()`或`notifyAll()`方法来唤醒它。        
2. **CyclicBarrier和CountDownLatch**：这两个类都位于`java.util.concurrent`包中，用于线程间的协调。`CyclicBarrier`允许一组线程互相等待，直到所有线程都达到某个状态后再继续执行；`CountDownLatch`则允许一个或多个线程等待其他线程完成操作后再执行。
3. **BlockingQueue**：BlockingQueue 是一个接口，它继承自 Queue 接口，提供了线程安全的队列操作。BlockingQueue 有多种实现类，如 ArrayBlockingQueue、LinkedBlockingQueue、PriorityBlockingQueue 等，它们都提供了阻塞的插入和取出操作。 


## 高级并发

### CompletableFuture


### Fork/Join框架

### ThreadLocal





1. ​线程基础​：如何创建和启动线程，线程的状态有哪些，start() 方法和 run() 方法的区别。
2. ​线程同步​：synchronized 关键字的作用和使用方法，以及 Lock 接口的优势。synchronized 是隐式锁，由 JVM 自动管理；Lock 是显式锁，需要手动获取和释放，且支持公平锁和非公平锁。
3. 线程池​：线程池的七大核心参数是什么，线程池的状态有哪些，在哪些场景下会用到多线程？常见的线程池有 FixedThreadPool、CachedThreadPool、SingleThreadExecutor、ScheduledThreadPool 等。
4. ​线程间通信​：wait()、notify()、notifyAll() 方法的作用和使用场景，以及 CyclicBarrier 和 CountDownLatch 的区别。
5. ​死锁​：什么是死锁，如何避免和解决死锁问题。


# 设计模式





# 数据库

1. ​SQL语句​：熟练掌握基本的 SQL 查询语句，如 SELECT、INSERT、UPDATE、DELETE 等，以及高级用法，如子查询、联结查询、分组查询、聚合函数等。
2. ​数据库设计​：理解数据库设计的规范化理论，包括第一范式、第二范式、第三范式等，以及如何设计关系型数据库和规范化表格。
3. ​索引​：索引的作用和原理，索引的类型（如 B 树索引、哈希索引），以及索引的优化和失效情况。
4. ​事务与锁机制​：事务的 ACID 属性，锁的类型（行级锁、表级锁、共享锁、排他锁等），以及它们对数据库性能的影响。

# 框架
​1. Spring框架​：Spring 的核心概念，如依赖注入（DI）、面向切面编程（AOP）、Spring MVC 等。Spring 是一个轻量级的控制反转（IoC）和面向切面编程（AOP）的容器框架，用于构建企业级应用程序。
2. ​Hibernate或MyBatis​：理解对象关系映射（ORM）的概念，以及 Hibernate 或 MyBatis 等 ORM 框架的工作原理和使用方法。Hibernate 是一个全自动的 ORM 框架，MyBatis 则是一个半自动的 SQL 映射框架。
3. ​Spring Boot​：Spring Boot 的特点和优势，以及如何使用 Spring Boot 快速搭建项目。
4. ​Spring Cloud​：了解微服务架构和 Spring Cloud 的核心组件，如服务注册与发现（Eureka、Consul）、配置中心（Config Server）、熔断器（Hystrix）等。



# 优化
1. 代码优化（提高代码性能和可维护性）
2. 数据库优化（优化SQL查询，提高查询速度。优化数据库性能，通过创建复合索引并采用最佳左前缀原则优化SQL查询效率，加快了数据处理速度）



