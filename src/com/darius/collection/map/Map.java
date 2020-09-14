package com.darius.collection.map;

/**
 * Create by im_dsd 2020/9/12 12:07 下午
 */
interface Map<K, V> {

    V put(K key, V value);

    V get(K key);

    Integer size();

    interface Entry<K, V> {

        K getKey();

        V getValue();
    }
}
