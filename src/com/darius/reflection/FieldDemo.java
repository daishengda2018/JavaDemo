package com.darius.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Create by im_dsd 2020/10/7 6:00 下午
 */
class FieldDemo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // ---- Demo1：Class 上关于 Field 的基础函数 ---
        // 1：Filed getField(String name) 根据名称获取某个 public 的字段（包含父类）
        // 2：Filed getDeclaredField(String) 根据名称获取某个自身的字段（不包括父类的）
        //    相比 getField 它能获取到非 public 修饰的字段：private、protected
        // 3: Field[] getFields()：获取所有public的field（包括父类）
        // 4: Field[] getDeclaredFields()：获取当前类的所有field（不包括父类）
        Class<Student> studentClass = Student.class;
        // A. 获取自身的 public 的 score
        System.out.println(studentClass.getField("score"));
        // B. 获取父类的 public 的 name
        System.out.println(studentClass.getField("name"));
        // C. 获取自身 private 的 grade
        // -> 使用 getField 会 java.lang.NoSuchFieldException: grade
        // -> System.out.println(studentClass.getField("grade"));
        // ！！正确方式 : getDeclaredField
        System.out.println(studentClass.getDeclaredField("grade"));

        // --- Demo2: Field 的方法 ---
        // 1. getName: 字段的名字
        // 2. getType：
        Field value = String.class.getDeclaredField("value");
        //
        System.out.println("name = " + value.getName());
        // class [C ；[C 表示 char 数组
        System.out.println("type = " + value.getType());
        int modifiers = value.getModifiers();
        System.out.println("isFinal = " + Modifier.isFinal(modifiers));
        System.out.println("isPublic = " + Modifier.isPublic(modifiers));
        System.out.println("isProtected = " + Modifier.isProtected(modifiers));
        System.out.println("isPrivate = " + Modifier.isPrivate(modifiers));
        System.out.println("isStatic = " + Modifier.isStatic(modifiers));

        // --- Demo3: 获取字段的值 ---
        Person person = new Person("Xiao Ming", 18);
        Class<? extends Person> pClass = person.getClass();
        Field name = pClass.getDeclaredField("name");
        System.out.println(name.get(person));
        Field age = pClass.getDeclaredField("age");
        // age 是私有字段，需要使用 setAccessible ，其含义为别管这个字段是不是public，一律允许访问。
        age.setAccessible(true);
        // get 的参数是对象的实例
        System.out.println(age.get(person));
        // 设置 value
        age.set(person, 28);
        System.out.println(age.get(person));
    }
}

class Student extends Person {
    public int score;
    private int grade;

    public Student(String name, int age) {
        super(name, age);
    }
}

class Person {
    public String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
