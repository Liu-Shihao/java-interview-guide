package com.lsh.java;

/**
 * volatile 是 Java 中用于保证变量可见性和禁止指令重排序的关键字，它是 Java 轻量级的同步机制之一。
 */
public class Class04_Volatile {

    /**
     * 核心特性
     * 1. 可见性保证 (Visibility)
     * 当一个线程修改了 volatile 变量的值，新值会立即被刷新到主内存中
     *
     * 当其他线程读取该变量时，会直接从主内存中读取最新值，而不是使用线程本地缓存(工作内存)
     *
     * 2. 禁止指令重排序 (Ordering)
     * JVM 和 CPU 会对指令进行优化重排，但 volatile 会禁止这种重排序
     *
     * 通过插入内存屏障(Memory Barrier)实现
     *
     * 使用volatile修饰 bool值 状态标志
     */
    private volatile boolean shutdownRequested;

    public void shutdown() {
        shutdownRequested = true;
    }

    public void doWork() {
        while (!shutdownRequested) {
            // 执行任务
        }
    }


    /**
     * 单例模式的双重检查锁定(DCL)
     */
    public static class Singleton {
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
}
