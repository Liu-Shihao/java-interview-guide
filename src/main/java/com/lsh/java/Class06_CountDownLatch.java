package com.lsh.java;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 是 Java 并发包 (java.util.concurrent) 中一个非常有用的同步辅助类，它允许一个或多个线程等待其他线程完成操作后再继续执行。
 */
public class Class06_CountDownLatch {

    // 1. 主线程等待多个子线程完成
    private static void test1(){
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
        /**
         * 主线程等待子线程完成任务...
         * Thread-0 完成任务
         * Thread-2 完成任务
         * Thread-1 完成任务
         * 所有子线程完成任务，主线程继续执行
         */
    }

    // 2. 多个线程等待一个信号同时开始
    private static void test2() throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 准备就绪，等待开始");
                    startSignal.await(); // 所有线程在此等待

                    // 收到信号后开始执行
                    System.out.println(Thread.currentThread().getName() + " 开始执行任务");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(1000); // 模拟准备工作
        System.out.println("发出开始信号");
        startSignal.countDown(); // 释放所有等待线程

        /**
         * Thread-0 准备就绪，等待开始
         * Thread-3 准备就绪，等待开始
         * Thread-1 准备就绪，等待开始
         * Thread-4 准备就绪，等待开始
         * Thread-2 准备就绪，等待开始
         * 发出开始信号
         * Thread-0 开始执行任务
         * Thread-1 开始执行任务
         * Thread-2 开始执行任务
         * Thread-4 开始执行任务
         * Thread-3 开始执行任务
         */
    }

    // 3.混合使用（等待开始+等待结束）
    private static void test3() throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 等待开始");
                    startSignal.await();

                    // 执行任务
                    System.out.println(Thread.currentThread().getName() + " 处理任务");
                    Thread.sleep((long)(Math.random() * 1000));

                    doneSignal.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // 主线程准备阶段
        Thread.sleep(1000);
        System.out.println("准备完成，发出开始信号");
        startSignal.countDown();

        doneSignal.await();
        System.out.println("所有线程完成任务");
        /**
         * Thread-2 等待开始
         * Thread-1 等待开始
         * Thread-3 等待开始
         * Thread-0 等待开始
         * Thread-4 等待开始
         * 准备完成，发出开始信号
         * Thread-1 处理任务
         * Thread-0 处理任务
         * Thread-3 处理任务
         * Thread-4 处理任务
         * Thread-2 处理任务
         * 所有线程完成任务
         */
    }
    public static void main(String[] args) throws InterruptedException {

//        test1();
//        test2();
        test3();


    }
}
