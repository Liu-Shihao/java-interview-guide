package com.lsh.java;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Java 中的 Lock 接口是 Java 并发包 (java.util.concurrent.locks) 中提供的一个比内置 synchronized 关键字更灵活的线程同步机制。
 */
public class Class03_ReentrantLock {

    private static int count = 0;

    public static void incrementWithReentrantLock() {
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            count++;
        } finally {
            lock.unlock(); // 确保在finally块中释放锁
        }
        
    }

    private int updateWithReentrantReadWriteLock(){
        ReadWriteLock rwLock = new ReentrantReadWriteLock();
        Lock readLock = rwLock.readLock();
        Lock writeLock = rwLock.writeLock();

        // 读操作
        readLock.lock();
        try {
            // 读取共享数据
            System.out.println("now count value: "+count);
        } finally {
            readLock.unlock();
        }

        // 写操作
        writeLock.lock();
        try {
            // 修改共享数据
            count++;
        } finally {
            writeLock.unlock();
        }
        return count;
    }


    
    public static void main(String[] args) {


        

    }


}
