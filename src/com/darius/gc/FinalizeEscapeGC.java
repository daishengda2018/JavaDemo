package com.darius.gc;

import javax.print.attribute.SupportedValuesAttribute;

/**
 * 通过 finalize() 方法躲避GC
 * Create by im_dsd 2020/8/3 16:31
 */
class FinalizeEscapeGC {
    private static FinalizeEscapeGC SAVE;
    private String name = "";

    private void isAlive() {
        System.out.println(name + "我还想在活500年！哈哈哈哈，我自救成功了！");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(name + "要死了吗？不，我要自救。");
        // 不想死把自己存起来
        SAVE = this;
    }

    public static void main(String[] args) throws InterruptedException {
        FinalizeEscapeGC finalizeEscapeGC = new FinalizeEscapeGC();
        finalizeEscapeGC.name = "小强";
        // 置空引用，等待 GC
        finalizeEscapeGC = null;
        System.gc();
        Thread.sleep(1000);
        if (SAVE != null) {
            SAVE.isAlive();
        } else {
            System.out.println("小强已经死翘翘，第一次");
        }

        // 再次不可达
        SAVE = null;
        System.gc();
        Thread.sleep(1000);
        if (SAVE != null) {
            SAVE.isAlive();
        } else {
            System.out.println("小强已经死翘翘，第二次");
        }
    }
}
