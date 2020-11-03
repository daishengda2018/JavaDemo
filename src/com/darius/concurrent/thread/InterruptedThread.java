package com.darius.concurrent.thread;

/**
 * 终止线程
 * <p>
 * Create by im_dsd 2020/9/22 9:49 上午
 */
class InterruptedThread {

    public static void main(String[] args) {
        MoonRunner runner = new MoonRunner();

    }

    private static class MoonRunner implements Runnable {
        private long i;

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                i++;
                System.out.println("i=" + i);
            }
            System.out.println("stop");
        }
    }
}
