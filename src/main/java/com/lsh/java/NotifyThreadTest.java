package com.lsh.java;

public class NotifyThreadTest {

    public static void main(String[] args) {

        Object object = new Object();
        Thread t1 = new Thread(() ->{
            synchronized (object){
                System.out.println("t1 获得锁，调用wait方法，进入WAITING状态");
                try {
                    object.wait();
                    System.out.println("t1 被唤醒,继续执行..");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
            }
        });

        Thread t2 = new Thread(() ->{
            synchronized (object){
                System.out.println("t2 获得锁，调用notify方法");
                object.notify();
                System.out.println("t2 继续执行..");
            }
        });
        t1.start();
        t2.start();
        /**
         * t1 获得锁，调用wait方法，进入WAITING状态
         * t2 获得锁，调用notify方法
         * t2 继续执行..
         * t1 被唤醒,继续执行..
         */
    }
    
}
