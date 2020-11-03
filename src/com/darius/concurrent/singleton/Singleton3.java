package com.darius.concurrent.singleton;

/**
 * 从并发的角度看单例
 * <p>
 * 完美的双重校验
 * <p>
 * Create by im_dsd 2020/9/21 10:16 上午
 */
class Singleton3 {
    private static volatile Singleton3 sInstance;

    private Singleton3() {
    }

    public static synchronized Singleton3 getInstance() {
        if (sInstance == null) {
            synchronized (Singleton3.class) {
                if (sInstance == null) {
                    sInstance = new Singleton3();
                }
            }
        }
        return sInstance;
    }
}
