package com.darius.gc;

/**
 * 内存分配与回收实验
 * <p>
 * Create by im_dsd 2020/8/3 17:41
 */
class MinorGCDemo {
    private static final int _1M = 1024 * 1024;

    /**
     * VM 参数 -verbose:gc -XX:+UseSerialGC -XX:+PrintHeapAtGC -XX:+PrintGCDetails -XX:SurvivorRatio=8 -Xms20M -Xmx20M -Xmn10M
     */
    public static void main(String[] args) {
        final byte[] allocation1 = new byte[2 * _1M];
        final byte[] allocation2 = new byte[2 * _1M];
        final byte[] allocation3 = new byte[2 * _1M];
        System.out.println("---------allocation4 start---------\n");
        // 此处将发生一次 Minor GC
        final byte[] allocation4 = new byte[2 * _1M];
        System.out.println("\n---------allocation4 end---------\n");
    }
}
