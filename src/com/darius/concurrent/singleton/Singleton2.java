package com.darius.concurrent.singleton;

/**
 * 从并发的角度看单例
 * 双重校验
 * <p>
 * 问题：指令重排导致 error
 * <p>
 * Create by im_dsd 2020/9/21 10:16 上午
 */
class Singleton2 {
    // 无法保证指令顺序
    private static Singleton2 sInstance;

    private Singleton2() {
    }

    public static synchronized Singleton2 getInstance() {
        if (sInstance == null) {
            synchronized (Singleton2.class) {
                // 无法保证指令顺序，
                // 此时的 sInstance 可能指向的是尚未初始化完成的对象
                // 其他线程访问会崩溃
                sInstance = new Singleton2(); // error
            }
        }
        return sInstance;
    }
}
