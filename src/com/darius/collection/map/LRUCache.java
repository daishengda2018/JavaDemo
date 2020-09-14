package com.darius.collection.map;

import java.util.HashMap;
import java.util.Map;

/**
 * LRUCache 的简单实现
 * 原理：使用双向链表维护顺序，使用 HashMap 作映射
 * <p>
 * Create by im_dsd 2020/9/14 12:03 上午
 */
public class LRUCache {
    /**
     * 映射存储
     */
    private final Map<Integer, Node> mMap;
    /**
     * 节点的顺序
     */
    private final NodeLink mNodeLink;
    private final int mCapacity;

    public LRUCache(int capacity) {
        mCapacity = capacity;
        mMap = new HashMap<>(capacity);
        mNodeLink = new NodeLink();
    }

    public int get(int key) {
        int result = -1;
        if (mMap.containsKey(key)) {
            // 移动节点到链表的头部
            Node node = mMap.get(key);
            result = node.value;
            mNodeLink.remove(node);
            mNodeLink.addFirst(node);
        }
        return result;
    }

    public void put(int key, int value) {
        Node node = new Node(key, value);
        if (mMap.containsKey(key)) {
            // 已经存在节点更新数据
            mMap.put(key, node);
            mNodeLink.remove(node);
            mNodeLink.addFirst(node);
        } else if (mNodeLink.size() >= mCapacity) {
            // 超过容量移除最后一个元素
            Node delete = mNodeLink.removeLast();
            if (delete != null) {
                // ！！！ Node 必须存储 key，才能定位到 map 中的位置
                mMap.remove(delete.key);
            }
            mMap.put(key, node);
        } else {
            // 直接添加
            mNodeLink.addFirst(node);
            mMap.put(key, node);
        }
    }

    /**
     * 双向链表的节点
     */
    private static class Node {
        Node pre, next;
        int key, value;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 用户维护 Node 顺序的链表
     */
    private static class NodeLink {
        private int mSize;
        private final Node mHead;
        private final Node mTail;

        public NodeLink() {
            mHead = new Node(Integer.MIN_VALUE, Integer.MIN_VALUE);
            mTail = new Node(Integer.MIN_VALUE, Integer.MIN_VALUE);
            mHead.next = mTail;
            mTail.pre = mHead;
        }

        void addFirst(Node key) {
            if (key == null) {
                return;
            }
            Node next = mHead.next;
            mHead.next = key;
            key.next = next;
            next.pre = key;
            key.pre = mHead;
            mSize++;
        }

        boolean remove(Node node) {
            if (node == null || node.pre == null || node.next == null) {
                return false;
            }
            Node next = node.next;
            node.pre.next = node.next;
            next.pre = node.pre;
            mSize--;
            return true;
        }

        Node removeLast() {
            if (mSize <= 0 || mTail.pre.pre == null) {
                return null;
            }
            Node delete = mTail.pre;
            delete.pre.next = mTail;
            mTail.pre = delete.pre;
            mSize--;
            return delete;
        }

        int size() {
            return mSize;
        }
    }
}
