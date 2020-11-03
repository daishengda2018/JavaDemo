package com.darius.reflection.test;

/**
 * Create by im_dsd 2020/10/8 6:19 下午
 */

public interface RequestListener<T> {
    /**
     * 请求回调
     * @param error 请求成功, 该值为null; 反之不为null,
     * @param result 请求成功, 不为null;
     */
    void onComplete(RequestError error, T result);
}