package com.darius.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Create by im_dsd 2020/9/30 5:30 下午
 */
class ReflectionDemo extends ArrayList<String> {

    public static void main(String[] args) throws Exception {
        BarFoo barFoo = new BarFoo();
        Type[] genericInterfaces = barFoo.getClass().getGenericInterfaces();
        Object result = null;
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                final ParameterizedType type = (ParameterizedType) genericInterface;
                final boolean isTarget = type.getRawType().getTypeName().equals(FooBar.class.getName());
                if (isTarget) {
                    result = parserType(type);
                }
            }
        }
    }

    private static Object parserType(ParameterizedType genericInterface) throws Exception {
        Type[] arguments = genericInterface.getActualTypeArguments();
        for (Type argument : arguments) {
            ParameterizedType argumentType = (ParameterizedType) argument;
            if (argumentType.getRawType() == null) {
                return null;
            }
            if (argumentType.getRawType().getTypeName().equals(List.class.getName())) {
                return Collections.emptyList();
            }
        }
        return null;
    }

    private static void test(List<String> instance) {

    }

    public static Boolean getBoolean() {
        return false;
    }

    public static List<Integer> getNumbers() {
        return null;
    }

    public static List<String> getStrings() {
        return null;
    }

    public interface FooBar<T> {
    }

    public static class BarFoo implements Runnable, FooBar<List<String>> {
        @Override
        public void run() {

        }
    }
}
