package com.lsh.java;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Java 原子类是 java.util.concurrent.atomic 包下的一组类，它们提供了一种线程安全的方式来操作单个变量，无需使用同步锁（如 synchronized）。
 */
public class Class05_AtomicClass {

    public static void main(String[] args) {

        //基本数据类型更新
        AtomicInteger counter = new AtomicInteger(0);
        // 线程安全递增
        counter.incrementAndGet();
        // 线程安全累加
        counter.addAndGet(10);

        //引用类型更新
        AtomicReference<String> ref = new AtomicReference<>("initial");
        // 原子更新
        ref.compareAndSet("initial", "updated");

        //带版本号，解决ABA问题
        AtomicStampedReference<Integer> stampedRef =
                new AtomicStampedReference<>(100, 0);

        // 获取当前值和版本号
        int[] stampHolder = new int[1];
        int currentValue = stampedRef.get(stampHolder);
        int currentStamp = stampHolder[0];

        // 带版本号的CAS操作
        stampedRef.compareAndSet(currentValue, 200, currentStamp, currentStamp + 1);


    }
}
