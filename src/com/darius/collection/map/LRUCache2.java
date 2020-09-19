package com.darius.collection.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Create by im_dsd 2020/9/14 5:27 下午
 */
class LRUCache2 {

    public LRUCache2() {
        new LinkedHashMap<Integer, Integer>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return super.removeEldestEntry(eldest);
            }
        };
    }
}
