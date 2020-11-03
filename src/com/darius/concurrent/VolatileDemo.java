package com.darius.concurrent;


/**
 * 在我的机器上不知道为什么，直接 run main 方法用则程序永远不会停止
 * 而是用 debug 方式运行，着很快得到结果
 * 我的机器配置：
 *  JDK: 1.8_121
 *  CPU: i7 8705G 4核8线程
 *  Mem：32G
 *  OS： macOs 10.15.16
 * Create by im_dsd 2020/9/21 12:05 上午
 */
class VolatileDemo {
    public static volatile int race = 0;

    public static void increase() {
        race++;
    }

    private static final int THREAD_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println("race=" + race);
    }
}
