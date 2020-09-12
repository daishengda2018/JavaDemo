package com.darius.gc;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 测试弱引用进入应用队列的时机：GC 标记后便进入（finalize 和 回收之前）
 *
 * Create by im_dsd 2020/8/19 01:42
 */
class WeakReferenceDemo {
    private static final int _1M = 1024 * 1024;

    /**
     * VM 参数 -XX:+UseSerialGC -XX:SurvivorRatio=8 -Xms20M -Xmx20M -Xmn10M
     */
    public static void main(String[] args) {
        Allocation allocation1 = new Allocation("张三", 2);
        Allocation allocation2 = new Allocation("王五", 2);
        Allocation allocation3 = new Allocation("赵四", 2);
        ReferenceQueue<Allocation> queue = new ReferenceQueue<>();
        WeakReference<Allocation> reference = new WeakReference<>(allocation3, queue);
        System.out.println("原始: " + reference.get());
        System.out.println("原始 queue:" + queue.poll() + "\n");
        allocation3.setRefInfo(reference, queue);
        // 置为 null,可以被 GC 回收
        allocation3 = null;
        // 这里将会发生一次 GC，此时将会尝试回收 allocation3
        Allocation allocation4 = new Allocation("李雷", 4);
        System.out.println("GC:" + reference.get());
        System.out.println("GC queue:" + queue.poll() + "\n");
    }

    private static class Allocation {
        private final byte[] allocation;
        private static Allocation SAVE;
        private final String mName;
        private WeakReference<Allocation> mReference;
        private ReferenceQueue<Allocation> mQueue;

        public Allocation(String name, int sizeMB) {
            mName = name;
            allocation = new byte[sizeMB * _1M];
        }

        public void setRefInfo(WeakReference<Allocation> reference, ReferenceQueue<Allocation> queue) {
            mReference = reference;
            mQueue = queue;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("finalize:" + mReference.get());
            System.out.println("finalize queue:" + mQueue.poll());
            super.finalize();
            System.out.println(mName + "  我要死了吗？不，我要自救。" + "\n");
            SAVE = this;
        }
    }
}
