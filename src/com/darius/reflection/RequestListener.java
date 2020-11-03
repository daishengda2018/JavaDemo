package com.darius.reflection;


/**
 * 网络请求回调
 * @param <T> 最终的数据类型, 例如 List<Feed>、User 等
 */
public interface RequestListener<T> {
    /**
     * 请求回调
     * @param error 请求成功, 该值为null; 反之不为null,
     * @param result 请求成功, 不为null;
     */
    void onComplete(RequestError error, T result);
}
