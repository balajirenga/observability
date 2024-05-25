package com.homeobserver.framework.core.handler;

import java.util.HashMap;

public class ThreadLocalBag {

    private static ThreadLocal<Object> threadLocalVariable = new ThreadLocal<>();
    private static HashMap<String, Object> threadLocalMap = new HashMap<>();

    static {
        threadLocalVariable.set(threadLocalVariable);
    }

    public static int count = 0;
    public static boolean setThreadLocalValue(Object value) {
        threadLocalVariable.set(value);
        count++;
        return true;
    }

    public static Object getThreadLocalValue() {
        return threadLocalVariable.get();
    }

    public static boolean setThreadLocalMapValue(String key, Object value) {
        threadLocalMap.put(key, value);
        count++;
        return true;
    }
    public static Object getThreadLocalMapValue(String key) {
        return threadLocalMap.get(key);
    }
}