package com.hb.test.springsecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码编码测试类
 *
 * @version v0.1, 2021/4/18 0:26, create by huangbiao.
 */
public class PasswordEncoderTest {

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("456");
        System.out.println(encode);
    }

}
