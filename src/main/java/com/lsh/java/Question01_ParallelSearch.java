package com.lsh.java;


import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.CompletableFuture;

/**
 * 并行查找 / 多线程搜索并终止
 * 题目：
 * 给定一个 大数组 和一个目标值 target，使用 多线程 并行搜索该数组。
 * 如果 任意一个线程 找到目标值，立即输出 "Found It!" 并 终止所有其他线程。
 * 如果没有找到，在所有线程完成后输出 "Not Found"。
 * 
 */
public class Question01_ParallelSearch {

    // 使用AtomicBoolean原子类或者使用volatile关键字修饰（保证变量可见性）
    // private static AtomicBoolean found = new AtomicBoolean(false);
    // 使用static修饰变量，可以保证所有线程明确共享同一个对象实例
    private static volatile boolean found = false;

    private static int threadCount = Runtime.getRuntime().availableProcessors();//8

    /**
     * 写法一：使用AtomicBoolean原子类或者定义volatile关键字修饰 boolean变量 保证线程间的可见性
     * 开启x个线程，每个线程按照一段范围查找
     * segmentSize = arr.length / x
     * start位置: i x segmentSize
     * end位置：(i+1) x segmentSize, 如果是最后一个线程，end位置为数组长度
     * 每个线程从start位置开始遍历，j<end 并且判断found变量
     * 如果 arr[j] == target,将found变量修改为true，输入日志，return
     * @param arr
     * @param target
     */
    public static void parallelSearch1(int[] arr, int target){
        int segmentSize = arr.length / threadCount;
        //创建8个线程
        for(int i = 0; i < threadCount; i++){
            //如果想在Lembda表达式中使用外部变量，该变量必须使用final修饰或者不可变
              final int start = i * segmentSize;
              //计算结束位置，如果是最后一个线程，结束位置为数组长度
              final int end = (i == threadCount - 1) ? arr.length : (i + 1) * segmentSize;  
              new Thread(()->{
                for(int j = start; j < end &&  !found; j++){
                    if (arr[j] == target){
                        found = true;
                        System.out.println("Found It!");
                        return;
                    }
                }
              }).start();
        }
        //如果没有找到，在所有线程完成后输出 "Not Found"
        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        if (!found){
            System.out.println("Not Found");
        }
    }

    /**
     * 写法二： 使用线程池ExecutorService + Future方式
     * @param arr
     * @param target
     */
    public static void parallelSearch2(int[] arr, int target){
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        AtomicBoolean found = new AtomicBoolean(false);
        int sgementSize = arr.length / threadCount;
        for(int i = 0; i < threadCount; i++){
            final int start = i * sgementSize;
            final int end  = (i == threadCount - 1)? arr.length : (i + 1) * sgementSize;
            executor.submit(() ->{
                for(int j = start; j < end && !found.get(); j++){
                    if (arr[j] == target){
                        found.set(true);
                        System.out.println("Found It!");
                        return;
                    }
                }
            });
        }
        executor.shutdown();//关闭线程池，不再接受新任务，但会继续执行已提交的任务
        //检查线程池是否已完全终止（所有任务执行完成且线程池关闭）。
        while (!executor.isTerminated()){
            //如果线程池还未
            Thread.yield();
        }
        if (!found.get()){
            System.out.println("Not Found");
        }
        System.out.println("Done");
    }

    /**
     * 写法三： 使用CompletableFuture实现
     * @param arr
     * @param target
     */
    public static void parallelSearch3(int[] arr, int target){
        AtomicBoolean found = new AtomicBoolean(false);
        int sgementSize = arr.length / threadCount;
        CompletableFuture<?>[] futures = new CompletableFuture[threadCount];
        for(int i = 0; i < threadCount; i++){
            final int start = i * sgementSize;
            final int end  = (i == threadCount - 1)? arr.length : (i + 1) * sgementSize;
            //使用CompletableFuture.runAsync()方法创建一个异步任务,并接收返回的CompletableFuture对象。
            futures[i] = CompletableFuture.runAsync(()->{
                for(int j = start; j < end; j++){
                    if (found.get()) {
                        return;
                    }
                    if (arr[j] == target) {
                        found.set(true);
                        System.out.println("Found It!");
                    }
                }
            });
        }
        //allOf()方法等待所有任务完成。
        //join()方法阻塞当前线程，直到所有任务完成。
        CompletableFuture.allOf(futures).join();
        if (!found.get()){
            System.out.println("Not Found");
        }
        System.out.println("Done");
        
    }

    public static void main(String[] args) {
        
        // System.err.println(""+threadCount);
        int[] arr = new int[1000000];
        for(int i = 0; i < arr.length; i++){
            arr[i] = (int)(Math.random() * 1000000);
        }
        int index =(int)(Math.random() * 1000000);
        int target = (int)(Math.random() * 1000000);
        arr[index] = target;
        System.out.println("target:"+target);
        parallelSearch3(arr, target);
        
    }

    
}
