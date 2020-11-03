package com.darius.reflection;

import com.darius.reflection.test.BooleanListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import sun.reflect.ConstantPool;

/**
 * Create by im_dsd 2020/10/8 4:20 下午
 */
class DefaultResult {
    private static final Method getConstantPoolMethod;
    private static final Map<Class<?>, Object> DEFAULT_RESULT_MAP = new HashMap<>();

    private static RequestListener<Boolean> mListener = (error, result) -> {

    };

    static {
        try {
            getConstantPoolMethod = Class.class.getDeclaredMethod("getConstantPool");
            getConstantPoolMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Couldn't find the 'getConstantPool' method - are you using the SUN JVM?", e);
        }

        DEFAULT_RESULT_MAP.put(Boolean.class, false);
        DEFAULT_RESULT_MAP.put(Byte.class, (byte) 0);
        DEFAULT_RESULT_MAP.put(Character.class, '\000');
        DEFAULT_RESULT_MAP.put(Double.class, 0.0d);
        DEFAULT_RESULT_MAP.put(Float.class, 0.0f);
        DEFAULT_RESULT_MAP.put(Integer.class, 0);
        DEFAULT_RESULT_MAP.put(Long.class, 0L);
        DEFAULT_RESULT_MAP.put(Short.class, (short) 0);
        DEFAULT_RESULT_MAP.put(String.class, "");
        DEFAULT_RESULT_MAP.put(List.class, Collections.emptyList());
        DEFAULT_RESULT_MAP.put(Boolean[].class, new Boolean[0]);
        DEFAULT_RESULT_MAP.put(Byte[].class, new byte[0]);
        DEFAULT_RESULT_MAP.put(Character[].class, new Character[0]);
        DEFAULT_RESULT_MAP.put(Double[].class, new Double[0]);
        DEFAULT_RESULT_MAP.put(Float[].class,new Float[0]);
        DEFAULT_RESULT_MAP.put(Integer[].class, new Integer[0]);
        DEFAULT_RESULT_MAP.put(Long[].class, new Long[0]);
        DEFAULT_RESULT_MAP.put(Short[].class, new Short[0]);
        DEFAULT_RESULT_MAP.put(String[].class, new String[0]);
    }

    public static void main(String[] args) {
        // lambda
        RequestListener<String> listener = (error, result) -> {
        };

        RequestListener<Boolean> listener1 = new RequestListener<Boolean>() {
            @Override
            public void onComplete(final RequestError error, final Boolean result) {
            }
        };
        BooleanListener listener2 = (error, result) -> {

        };
       RequestListener<String[]> listener3 = new RequestListener<String[]>() {
           @Override
           public void onComplete(RequestError error, String[] result) {

           }
       };
        System.out.println(DEFAULT_RESULT_MAP.get(resolveLambdaArgumentType(mListener, 1)));
        System.out.println(DEFAULT_RESULT_MAP.get(resolveLambdaArgumentType(listener, 1)));
        System.out.println(DEFAULT_RESULT_MAP.get(resolveLambdaArgumentType(listener1, 1)));
        System.out.println(DEFAULT_RESULT_MAP.get(resolveLambdaArgumentType(listener2, 1)));
        System.out.println(DEFAULT_RESULT_MAP.get(resolveLambdaArgumentType(listener3, 1)));
    }

    public static Class<?> resolveLambdaArgumentType(Object object, int argumentIndex) {
        Objects.requireNonNull(object, "object");
        if (argumentIndex < 0) {
            throw new IllegalArgumentException("ArgumentIndex must be >= 0");
        }

        Type genericInterface = object.getClass().getGenericInterfaces()[0];
        if (genericInterface instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        } else {
            String[] methodRef = resolveMethodRef(object, "Failed to resolve generic type arguments for " + object + " of type " + genericInterface.toString());
            try {
                String argumentType = jdk.internal.org.objectweb.asm.Type.getArgumentTypes(methodRef[2])[argumentIndex].getClassName();
                return Class.forName(argumentType);
            } catch (Exception e) {
                throw new RuntimeException("Failed to resolve generic type arguments for " + object + " of type " + genericInterface.toString(), e);
            }
        }
    }

    private static String[] resolveMethodRef(Object object, String message) {
        ConstantPool constantPool = getConstantPool(object, message);

        int at = 15;
        String[] methodRef = null;
        // Try and resolve the methodRef, but stop if advancing the at doesn't turn out a result
        while (methodRef == null && at < 100) {
            try {
                // This is pretty slow and somewhat idiotic, but works and gives us reified generics in Java 8
                methodRef = constantPool.getMemberRefInfoAt(at);
            } catch (IllegalArgumentException e) {
                // For every generic argument the index forward
                at += 1;
            }
        }
        if (methodRef == null) {
            throw new RuntimeException(message);
        }
        return methodRef;
    }
    private static ConstantPool getConstantPool(Object object, String message) {
        try {
            return (ConstantPool) getConstantPoolMethod.invoke(object.getClass());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(message, e);
        }
    }

    public <T> T buildDefaultResult(RequestListener<T> listener) throws Exception {
        T result = null;
        Class<?> type = firstParameterSmart(listener);
        Object defaultValue = DEFAULT_RESULT_MAP.get(type);
        result = (defaultValue == null ? null : (T) defaultValue);
        return result;
    }

    private static <T> Class<?> firstParameter(RequestListener<T> listener) {
        return listener.getClass().getMethods()[0].getParameterTypes()[1];
    }

    // Lambda class name: test.Toto$$Lambda$1/1199823423
    // Implementation synthetic method: lambda$main$0
    //
    private static <T> Class<?> firstParameterSmart(RequestListener<T> listener) {
        String functionClassName = listener.getClass().getName();
        int lambdaMarkerIndex = functionClassName.indexOf("$$Lambda$");
        if (lambdaMarkerIndex == -1) { // Not a lambda
            return firstParameter(listener);
        }

        String declaringClassName = functionClassName.substring(0, lambdaMarkerIndex);
        int lambdaIndex = Integer.parseInt(functionClassName.substring(lambdaMarkerIndex + 9, functionClassName.lastIndexOf('/')));

        Class<?> declaringClass;
        try {
            declaringClass = Class.forName(declaringClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to find lambda's parent class " + declaringClassName);
        }

        for (Method method : declaringClass.getDeclaredMethods()) {
            if (method.isSynthetic()
                    && method.getName().startsWith("lambda$")
                    && method.getName().endsWith("$" + (lambdaIndex - 1))
                    && Modifier.isStatic(method.getModifiers())) {
                return method.getParameterTypes()[1];
            }
        }

        throw new IllegalStateException("Unable to find lambda's implementation method");
    }
}
