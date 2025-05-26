package com.lsh.java;

/**
 * Java中的synchronized关键字是用于实现线程同步的核心机制
 * synchronized 是 Java 中用于实现线程同步的关键字，它可以确保多个线程在访问共享资源时的线程安全性
 *
 * synchronized 关键字只能用于：
 * 方法（实例方法和静态方法）
 * 代码块
 * synchronized 不能直接修饰属性（成员变量）
 */
public class Class02_Synchronized {

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

    private final Object lock = new Object();
    /**
     * synchronized修饰代码块
     * 锁对象可以指定任意对象（可以是示例或者Class对象）
     */
    public int syncCodeBlock(){
        synchronized(lock) {
            return count++;
        }
    }

}
