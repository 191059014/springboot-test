package com.hb.test.springsecurity.util;

/**
 * 模拟token
 *
 * @version v0.1, 2021/4/18 12:01, create by huangbiao.
 */
public class TokenMock {

    public static String getToken(String username) {
        if ("zhangsan".equals(username)) {
            return "zhangsan_token";
        } else if ("lisi".equals(username)) {
            return "lisi_token";
        } else {
            return "";
        }
    }

}
