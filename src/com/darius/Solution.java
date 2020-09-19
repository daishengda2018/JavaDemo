package com.darius;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    private Map<Integer, Node> mCache;
    private DoubleLinkedList mList;
    private int mCapacity;
    /**
     * lru design
     * @param operators int整型二维数组 the ops
     * @param k int整型 the k
     * @return int整型一维数组
     */
    public int[] LRU (int[][] operators, int k) {
        // write code here
        mCache = new HashMap<>();
        mList = new DoubleLinkedList();
        mCapacity = k;

        int resultLength = 0;
        for(int[] item : operators) {
            if(item.length > 0 && item[0] == 2) {
                resultLength++;
            }
        }
        if (resultLength == 0) {
            return new int[0];
        }
        final int[] result = new int[resultLength];
        for(int[] item : operators) {
            if (item.length == 0) {
                continue;
            }
            final int action = item[0];
            if (action == 1) {
                // put
            } else if (action == 2) {
                // get
                //
            }
        }
        return result;
    }

    private void put(int key, int value) {
        final Node node = new Node(key, value);
        mCache.put(key, node);
        mList.addFirst(node);
        if (mList.size < mCapacity) {
            return;
        }
        Node older = mList.removeLast();
        if (older != null) {
            mCache.remove(older.key);
        }
    }

    private int get(int key) {
        Node result = mCache.get(key);
        if (result == null) {
            return -1;
        }
        mList.remove(result);
        mList.addFirst(result);
        return result.value;
    }

    /**
     * 数据节点
     */
    private static class Node {
        int key, value;
        Node pre, next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 保存存储顺序的链表
     */
    private static class DoubleLinkedList {
        private final Node mHead, mTail;
        private int size;

        DoubleLinkedList() {
            mHead = new Node(Integer.MIN_VALUE, Integer.MIN_VALUE);
            mTail = new Node(Integer.MIN_VALUE, Integer.MIN_VALUE);
            mHead.next = mTail;
            mTail.pre = mHead;
        }

        /**
         * return older
         */
        public boolean remove(Node node) {
            if (size <= 0 || node == null || node.pre == null || node.next == null) {
                return false;
            }
            final Node preNode = node.pre;
            final Node nextNode = node.next;
            preNode.next = nextNode;
            nextNode.pre = preNode;
            size--;
            return true;
        }

        public void addFirst(Node node) {
            if (node == null) {
                return;
            }
            final Node next = mHead.next;
            mHead.next = node;
            node.pre = mHead;
            node.next = next;
            next.pre = node;
            size++;
        }

        public Node removeLast() {
            if(size <= 0 || mTail.pre == null || mTail.pre.pre == null) {
                return null;
            }
            Node node = mTail.pre;
            Node preNode = node.pre;
            preNode.next = mTail;
            mTail.pre = preNode;
            size--;
            return node;
        }

        public int size() {
            return size;
        }
    }
}