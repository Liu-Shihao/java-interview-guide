package com.lsh.java;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 * Java 线程创建的四种方式
 */
public class Class01_Thread {

    static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("MyThread "+currentThread().getName()+" run...");
        }
    }


    static class MyRunnable implements Runnable{

        @Override
        public void run() {
            System.out.println("MyRunnable "+Thread.currentThread().getName()+" run...");
        }
    }


    static class MyCallable implements Callable<String>{

        @Override
        public String call() throws Exception {
            return "MyCallable "+Thread.currentThread().getName()+" run...";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //1.集成Thread类，重写run方法
        new MyThread().start();

        //2.实现Runnable接口，重写run方法
        new Thread(new MyRunnable()).start();

        //3. Lambda 表达式

        new Thread(() -> System.out.println("Lambda "+Thread.currentThread().getName()+" run..."))
                .start();


        //4. 实现Callable接口，重写call方法，有返回值

        FutureTask<String> futureTask = new FutureTask<>(new MyCallable());
        new Thread(futureTask).start();
        System.out.println(futureTask.get());


        /**
         * MyRunnable Thread-1 run...
         * Lambda Thread-2 run...
         * MyThread Thread-0 run...
         * MyCallable Thread-3 run...
         */


    }
}
