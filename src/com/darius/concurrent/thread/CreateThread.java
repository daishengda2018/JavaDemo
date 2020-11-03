package com.darius.concurrent.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 创建线程的三种方式
 * Create by im_dsd 2020/9/22 9:36 上午
 */
class CreateThread {

    public static void main(String[] args) {
        new MyThread().start();
        new MyThread(new MyRunnable()).start();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        System.out.println();
        Future<String> future = executor.submit(new MyCallable());
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class MyThread extends Thread {
        public MyThread(Runnable target) {
            super(target);
        }

        public MyThread() {
        }

        @Override
        public void run() {
            super.run();
            String name = Thread.currentThread().getName();
            System.out.println(name + "create by extends Thread");
        }
    }

    private static class MyRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println();
            String name = Thread.currentThread().getName();
            System.out.println(name + " create by implements Runnable");
        }
    }

    private static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            String name = Thread.currentThread().getName();
            return name + " create by implements Callable";
        }
    }
}
