package com.darius.gc;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Java 多引用类型实验
 * <p>
 * Create by im_dsd 2020/8/3 16:42
 */
class ReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        // 强引用
        final Object object = new Object();

        // 软引用
        Object softStr =  new Object();
        ReferenceQueue<Object> softReferenceQueue = new ReferenceQueue<>();
        SoftReference<Object> softReference = new SoftReference<>(softStr, softReferenceQueue);
        System.out.println("soft:" + softReference.get());
        System.out.println("soft queue:" + softReferenceQueue.poll());
        // 手动 GC
        softStr = null;
        System.gc();
        Thread.sleep(1000);
        System.out.println("gc 之后的对象存活状态");
        System.out.println("soft:" + softReference.get());
        System.out.println("soft queue:" + softReferenceQueue.poll());
        System.out.println();

        // 弱引用
        Object weakStr =  new Object();
        ReferenceQueue<Object> weakReferenceQueue = new ReferenceQueue<>();
        WeakReference<Object> weakReference = new WeakReference<>(weakStr, weakReferenceQueue);
        System.out.println("weak:" + weakReference.get());
        System.out.println("weak queue:" + weakReferenceQueue.poll());
        // 手动 GC
        weakStr = null;
        System.gc();
        Thread.sleep(1000);
        System.out.println("gc 之后的对象存活状态");
        System.out.println("weak:" + weakReference.get());
        System.out.println("weak queue:" + weakReferenceQueue.poll());
        System.out.println();

        // 弱引用
         Object phantomStr =  new Object();
        ReferenceQueue<Object> phantomReferenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(phantomStr, phantomReferenceQueue);
        System.out.println("phantom:" + phantomReference.get());
        System.out.println("phantom queue:" + phantomReferenceQueue.poll());
        // 手动 GC
        phantomStr = null;
        System.gc();
        Thread.sleep(1000);
        System.out.println("gc 之后的对象存活状态");
        System.out.println("phantom:" + phantomReference.get());
        System.out.println("phantom queue:" + phantomReferenceQueue.poll());
        System.out.println();
    }
}
