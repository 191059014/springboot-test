package com.hb.test.springsecurity.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟redis
 *
 * @version v0.1, 2021/4/18 12:01, create by huangbiao.
 */
public class RedisMock {

    private static Map<String, String> cache = new HashMap<>();

    public static void set(String key, String value) {
        cache.put(key, value);
    }

    public static String get(String key) {
        return cache.get(key);
    }

    public static void remove(String key) {
        cache.remove(key);
    }

}
