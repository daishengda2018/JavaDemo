package com.darius.concurrent.singleton;

/**
 * 从并发的角度看单例
 * <p>
 * 问题：在并发下将会初始化多个实例
 * <p>
 * Create by im_dsd 2020/9/21 10:16 上午
 */
class Singleton0 {
    private static Singleton0 sInstance;

    private Singleton0() {

    }

    public static Singleton0 getInstance() {
        if (sInstance == null) {
            sInstance = new Singleton0();
        }
        return sInstance;
    }
}
