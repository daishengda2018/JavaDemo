package com.darius.concurrent.singleton;

/**
 * 从并发的角度看单例
 * 加锁
 * <p>
 * 问题：锁住了实例性能太差
 * <p>
 * Create by im_dsd 2020/9/21 10:16 上午
 */
class Singleton1 {
    private static Singleton1 sInstance;

    private Singleton1() {
    }

    // 使用 synchronized 锁住对象实例后，同一时间仅能有一个线程可以访问，其他线程都会阻塞
    // 性能太差了
    public static synchronized Singleton1 getInstance() {
        if (sInstance == null) {
            sInstance = new Singleton1();
        }
        return sInstance;
    }
}
