# 2025616 面经
Object类里有哪些方法？notify（）方法和notifyAll（）方法
wait方法和sleep方法
equeals 和hashcode 的关系
start方法和run方法
继承Thread和实现Runnable两种方式那种开销更大？问了AI，两种方式是一样的，执行效率无差别
抽象类和接口的区别
final关键字
自定义序列化
HashMap和hashtable的区别
JVM Heap堆和栈Stack？哪个速度快
JVM调优经验？JVM参数配置
有遇到OOM的情况吗？如何查看heap dump文件？工具？
堆内存分区：新生代（Eden/Survivor From、TO比例是多少）
垃圾回收算法
Java里有哪些引用类型？强软弱虚

Spring AOP是什么？与OOO的区别？
SpringIOC是什么？
SpringMCV的请求过程


JWT组成
非对称加密算法，私钥加密，公钥解密，为什么使用其他的公钥不能解密？
TCP/IP
http协议常见的请求头、Content-Type，上传文件时Content-Type应该用什么？ multipart/form-data
403/401
Docker
K8s Pod、Service
Istio 
MYSQL innodb 聚簇索引与非聚簇索引
数据库表结构你是如何设计？
Redis基本数据类型，String字符串用什么存储?
Redis为什么这么快？多路复用 epoll？
OAuth2.0流程
Embedding的实现过程 tokenizer
Transfermor 架构

算法题：
两数之和，时间和空间复杂度是多少？
反转链表
多线程题目：一个数组，找到其中的一个数，当一个线程找到了输出找到了，并停止其他线程
## Object类中的常用方法？ 
Object类中常用的方法有：equals()、hashCode()、toString()、notify()、notifyAll()、wait()等
- equals():判断两个对象是否相等，默认比较的是对象的地址，需要重写
- hashCode():返回对象的哈希码，用于哈希表等数据结构，需要重写
- toString():返回对象的字符串表示，默认返回类名@哈希码，需要重写
- notify():唤醒一个等待的线程（不确定是哪一个），需要在synchronized代码块中使用
- notifyAll():唤醒所有等待的线程，需要在synchronized代码块中使用
- wait():使当前线程等待，需要在synchronized代码块中使用 

（之前没有注意过这个问题，我只说出了toString/notify/notifyAll/wait()，并且还说错了`sleep()`方法，sleep方法是Thread类中的方法）
接着又问了notify方法和notifyAll方法你平常使用哪个？为什么？没有回答上来
- notify():随机唤醒一个等待线程，竞争低，仅一个线程尝试获取锁资源
- notifyAll():唤醒所有等待线程，竞争较高，所有线程争夺锁资源

所以使用notify方法更高效，线程竞争小，效率高。

两者必须在synchronized代码块中使用，否则会抛出IllegalMonitorStateException异常

当调用了`notify()`方法或者`notifyAll()`方法后，被唤醒的线程并不会立即执行，而是需要重新竞争锁，进入阻塞（`BLOCKED`）状态，知道获取到锁资源后才会继续执行。

调用notify方法后，不会立即释放锁，而是继续执行完当前的synchronized代码块后才会释放锁。

`notify()`方法会随机唤醒一个在该对象上wait()的线程（notifyAll()方法唤醒所有），被唤醒的线程会从`WAITING`状态转换为`BLOCKED`状态（因为需要重新竞争锁）。
code: [NotifyThreadTest.java](src/main/java/com/lsh/java/NotifyThreadTest.java)

### sleep方法和wait方法的区别？
- sleep()方法是 Thread类 的静态方法，直接通过 `Thread.sleep()` 调用; wait()方法是`Object类`的实例方法,如 `obj.wait()`
- **sleep()方法不会释放锁，即使线程休眠，它仍然持有同步锁（如synchronized块或方法中的锁）；wait()方法会释放锁，允许其他线程进入同步代码块。**
- 唤醒机制不同：sleep()方法休眠结束后，线程会自动恢复；而wait()方法需要手动调用notify()或notifyAll()方法来唤醒或者超时唤醒。


线程状态转换：
- 调用了`sleep`方法后，线程进入 `TIMED_WAITING` 状态，不释放锁。
- 调用了`wait`方法后，线程进入 `WAITING` 或者 `TIMED_WATTING` 状态，释放锁。
- 调用了`notify`方法后，被唤醒的线程进入 `BLOCKED` 状态​，需要重新竞争锁。

### equals和hashcode的关系
如果两个对象equals方法比较相等，那他们的hashcode一定相等；
而反过来，如果两个对象hashcode相等，那他们的equals方法不一定相等（值不一定相等）。

原因：因为哈希表（如HashMap）依赖hashcode快速定位对象，如果equals相等而hashcode不同，会导致哈希表无法正确定位对象。

HashMap依赖hashCode()和equals()方法：
- HashMap使用hashCode()方法来计算key的hash值，用于快速定位桶（Bucket）。
- equals方法用于用于在哈希冲突（hashCode相等）时比较键是否真正相等。

总结：
1. equals相等hashcode一定相等
2. hashcode相等equals不一定相等
3. 重写equals方法时，必须重写hashCode方法






## 算法题
前两道简单题，第三道考试多线程和同步机制
### 两数之和
https://leetcode.cn/problems/two-sum/
```java
public int twoSum(int[] nums, int target){
    if(nums == null || nums.length < 2) return null;
    HashMap<Integer ,Integer> map = new HashMap<>();
    for(int i = 0; i < nums.length; i++){
        if(map.containsKey(target - nums[i])){
            return new int[]{i, map.get(target - nums[i])};
        }    
        map.put(nums[i], i);
    }
    return new int[0];
}

```

code: [LeetCode_Hot01_TwoSum.java](src/main/java/com/lsh/arithmetic/LeetCode_Hot01_TwoSum.java)

### 反转连表
https://leetcode.cn/problems/reverse-linked-list/description/
```java
public class ListNode{
    public int value;
    public ListNode next;
}
```

1 迭代解法
```java
public reverseLinkedList(ListNode head){
    if(head == null) return null;
    ListNode prev = null;
    ListNode current = head;
    while(current != null){
       ListNode next = current.next;
       current.next = prev;
       prev = current;
       current = next;
    }
    return prev;
}
```

2.递归解法
```java
public ListNode reverseLinkedList(ListNode head){
    if(head == null || head.next == null) return head;
    ListNode newHead = reverseLinkedList(head.next);
    head.next.next = head;
    head.next = null;
    return newHead;
}
```

code：[LeetCode_Hot206_reverse_linked_list.java](src/main/java/com/lsh/arithmetic/LeetCode_Hot206_reverse_linked_list.java)

###  并行查找
多线程查找数组中的数，当一个线程找到时输出找到了，其他线程停止查找

使用volatile关键字或者AtomicBoolean原子类 定义found ，保证线程可见性，创建新线程，将数组按照创建的线程数分段，从start开始遍历，到end结束（注意，如果是最后一个线程，end为数组的长度）
可以使用CompletableFuture实现

code: [Question01_ParallelSearch.java](src/main/java/com/lsh/java/juc/Question01_ParallelSearch.java)


