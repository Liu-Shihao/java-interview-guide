package com.lsh.java;

import java.util.PriorityQueue;
import java.util.concurrent.*;

/**
 * 线程池类型
 * Java 提供了多种线程池实现，主要在 java.util.concurrent 包下。以下是一些常用的线程池类型：
 * * 1. Fixed Thread Pool（固定大小线程池）
 * *    - 使用 Executors.newFixedThreadPool(int nThreads) 创建
 * *    - 线程池中有固定数量的线程，适用于处理大量短期任务
 * * * 2. Cached Thread Pool（可缓存线程池）
 * *    - 使用 Executors.newCachedThreadPool() 创建
 * *    - 线程池根据需要创建新线程，空闲线程会被回收，适用于处理大量短期任务
 * * * 3. Single Thread Executor（单线程执行器）
 * *    - 使用 Executors.newSingleThreadExecutor() 创建
 * *    - 线程池中只有一个线程，适用于需要保证任务顺序执行的场景
 * * * 4. Scheduled Thread Pool（定时任务线程池）
 * *    - 使用 Executors.newScheduledThreadPool(int corePoolSize) 创建
 * *    - 线程池支持定时和周期性任务执行，适用于需要定时执行任务的场景
 * * * 5. Work Stealing Pool（工作窃取线程池）
 * *    - 使用 Executors.newWorkStealingPool() 创建
 * *    - 线程池支持工作窃取算法，适用于处理大量异步任务，能够动态调整线程数量
 *
 */
public class ThreadPoolsTest {


    /**
     * 线程池核心参数：
     * 1. corePoolSize：核心线程数，线程池中始终保持的线程数量。
     * 2. maximumPoolSize：最大线程数，线程池中允许的最大线程数量。
     * 3. keepAliveTime：线程空闲时的存活时间，超过该时间的空闲线程会被回收。
     * 4. workQueue：任务队列，用于存放待执行的任务。
     * 5. threadFactory：线程工厂，用于创建新线程。
     * 6. handler：拒绝策略，当线程池和队列都满了时，如何处理新提交的任务。
     * 7. timeUnit：时间单位
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        //核心线程数和最大线程数一样，默认使用LinkedBlockingQueue作为任务队列,无界队列
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);// 创建一个固定大小为5的线程池

        // execute 和 submit 方法都可以提交任务到线程池中执行
        // execute 方法不返回结果，适用于不需要返回值的任务
        // submit 方法可以返回 Future 对象，适用于需要返回值的任务
        fixedThreadPool.execute(() -> {
            // 执行任务的代码
            System.out.println("Task executed in fixed thread pool");
        });
        fixedThreadPool.submit(() -> {
            // 执行任务的代码
            System.out.println("Task submitted in fixed thread pool");
        });

        //默认创建核心线程数为0，最大线程数为Integer.MAX_VALUE，使用SynchronousQueue同步队列作为任务队列
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        //创建一个核心线程数和最大线程数为1的线程池，使用无界队列LinkedBlockingQueue
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();// 创建一个单线程执行器，只有一个线程处理任务

        //创建核心线程数为5，最大线程数为Integer.MAX_VALUE的线程池，使用DelayedWorkQueue作为任务队列
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);// 创建一个定时任务线程池，核心线程数为5

        //ArrayBlockingQueue 是一个有界阻塞队列，使用固定大小的数组作为底层存储结构。
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<>(10);


        arrayBlockingQueue.add("1");//add 方法插入元素，队列满时抛出 IllegalStateException
        arrayBlockingQueue.offer("2");// offer 方法插入元素，队列满时返回 false,（不抛异常）
        arrayBlockingQueue.put("3");// put 方法插入元素，队列满时阻塞等待，直到有空间可用,（BlockingQueue特有）

        arrayBlockingQueue.remove();//移除并返回队首元素，队列空时抛出 NoSuchElementException
        arrayBlockingQueue.poll();//移除并返回队首元素，队列空时返回 null
        arrayBlockingQueue.take();//移除并返回队首元素，队列空时阻塞等待，直到有元素可用,（BlockingQueue特有）


        //LinkedBlockingQueue 是一个基于链表的无界阻塞队列，使用链表作为底层存储结构。
        LinkedBlockingDeque<Object> linkedBlockingDeque = new LinkedBlockingDeque<>();

        SynchronousQueue<Object> synchronousQueue = new SynchronousQueue<>();

        //非线程安全， 不支持并发操作， 不支持阻塞操作
        PriorityQueue<Object> priorityQueue = new PriorityQueue<>();

        //PriorityBlockingQueue 相当于小顶堆，最小元素先出，线程安全，内置ReentrantLock
        PriorityBlockingQueue<Object> priorityBlockingQueue = new PriorityBlockingQueue<>();
        //大顶堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);


        //DelayedWorkQueue 是 ScheduledThreadPoolExecutor 的私有静态内部类,外部无法直接访问。它的设计初衷是专门用于支持 ScheduledThreadPoolExecutor 的定时任务调度。
//        DelayedWorkQueue delayedWorkQueue = new DelayedWorkQueue();
        DelayQueue<Delayed> delayQueue = new DelayQueue<>();


        //Rejection Policy 拒绝策略
        // 1. 直接抛出异常，默认策略
        ThreadPoolExecutor.AbortPolicy abortPolicy = new ThreadPoolExecutor.AbortPolicy();

        // 2. 直接丢弃新任务，不抛异常也不执行。
        ThreadPoolExecutor.DiscardPolicy discardPolicy = new ThreadPoolExecutor.DiscardPolicy();

        // 3. 丢弃队列中最早的任务，重新提交新任务。
        ThreadPoolExecutor.DiscardOldestPolicy discardOldestPolicy = new ThreadPoolExecutor.DiscardOldestPolicy();

        // 4. 将新任务放入调用者线程中执行，阻塞当前线程直到任务完成。
        ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();

        // 或者自定义拒绝策略，实现RejectedExecutionHandler 接口



    }
}
