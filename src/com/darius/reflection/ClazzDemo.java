package com.darius.reflection;

/**
 * Java 在运行时会为所有对象生成一个包装类：Class。通过 Class 我们可以获取到对象的任何信息
 *
 * Create by im_dsd 2020/10/7 5:00 下午
 */
class ClazzDemo {
    public static void main(String[] args) {
        // 获取 Class 的方法
        // 1. 类.class
        Class<String> stringClass = String.class;
        // 2. 实例.getClass
        String s = "Hello";
        Class<? extends String> aClass = s.getClass();
        // 二者是等价的
        System.out.println(stringClass == aClass);

        // instanceof 与 == 对于 Class 的区别
        Integer n = new Integer("123");
        // result = true;
        System.out.println(n instanceof Integer);
        // result = true: instanceof 会比较（包含父类的）类型
        System.out.println(n instanceof Number);
        // result = false；== 相比 instanceof 更加精确的比较类型，不包含子类父类的关系
        System.out.println(n.getClass() == Integer.class);
//        System.out.println(n.getClass() == Number.class);

        printClassInfo("".getClass());
        printClassInfo(Runnable.class);
        printClassInfo(java.time.Month.class);
        // 数组 getName 是特殊的 ：class name = [Ljava.lang.String;
        printClassInfo(String[].class);
        printClassInfo(int.class);

        try {
            stringClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void printClassInfo(Class<?> clazz) {
        System.out.println("class name = " + clazz.getName());
        System.out.println("Simple name = " + clazz.getSimpleName());
        if (clazz.getPackage() != null) {
            System.out.println("package name = " + clazz.getPackage().getName());
        }
        System.out.println("is Interface? : " + clazz.isInterface());
        System.out.println("is enum? : " + clazz.isEnum());
        System.out.println("is array? : " + clazz.isArray());
        System.out.println("is primitive? : " + clazz.isPrimitive());
    }
}
