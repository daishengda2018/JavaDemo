package com.darius.reflection;

import java.lang.reflect.Method;

/**
 *
 * Create by im_dsd 2020/10/7 11:07 下午
 */
class MethodDemo {
    public static void main(String[] args) throws Exception {
        // --- Demo1: 基本方法---
        // 1. Method getMethod(name, Class……) 获取 public 方法，包含父类
        // 2. Method getDeclaredMethod(name, Class……) 获取任意方法，不包含父类
        // 3. Method[] getMethods() 获取所有 public 方法列表，包含父类
        // 4. Method[] getDeclaredMethods() 获取任意方法列表，不包含父类
        Class<?> clazz = Student1.class;
        System.out.println(clazz.getMethod("getScore", String.class));
        System.out.println(clazz.getMethod("getName"));
        System.out.println(clazz.getDeclaredMethod("getGrade", int.class));

        // --- Demo2：Method 方法介绍---
        // 1. getName 获取名称
        // 2. getReturnType 获取返回值类型，也是一个Class实例，例如：String.class；
        // 3. getParameterTypes 获取参数列表类型, 然后结果是一个Class数组，例如：{String.class, int.class}；
        // 4. getModifiers 返回修饰符信息，返回值是一个 int 不同的 bit 有不同的含义。
        //##A. 调用方法
        String s = "Hello Word";
        Method method = String.class.getMethod("substring", int.class);
        Object result = method.invoke(s, 6);
        System.out.println(result);

        //##B. 调用静态方法
        Method parseInt = Integer.class.getMethod("parseInt", String.class);
        // 对象实例要传递 null
        int invoke = (int) parseInt.invoke(null, "123");
        System.out.println(invoke);

        //##C. 调用非 public 方法
        Person1 person = new Person1();
        // 使用不同的 get 方法是获取不到 private 方法的，必须使用 getDeclaredMethod
        Method setName = person.getClass().getDeclaredMethod("setName", String.class);
        // 在进行赋值操作的时候必须设置 setAccessible(true)
        // setAccessible(true) 可能会失败，如果JVM运行期存在SecurityManager
        // 那么它会根据规则进行检查，有可能阻止setAccessible(true)
        setName.setAccessible(true);
        setName.invoke(person, "DSD");
        System.out.println(person.getName());

        //##D. 多态
        // 运行下述代码，发现打印出的是Student:hello，
        // 因此，使用反射调用方法时，仍然遵循多态原则：即总是调用实际类型的覆写方法（如果存在）。上述的反射代码：
        Method hello = Person1.class.getMethod("hello");
        hello.invoke(new Student1());
    }
}
class Student1 extends Person1 {
    public int getScore(String type) {
        return 99;
    }
    private int getGrade(int year) {
        return 1;
    }

    public void hello() {
        System.out.println("Student:hello");
    }
}

class Person1 {
    private String name;
    private void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void hello() {
        System.out.println("Person:hello");
    }
}
