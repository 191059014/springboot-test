package com.hb.test;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 密码测试
 *
 * @author Mr.Huang
 * @version v0.1, PasswordTest.java, 2020/6/22 9:45, create by huangbiao.
 */
public class PasswordTest extends SpringbootTestEmailApplicationTest {

    @Autowired
    private StringEncryptor encryptor;

    @Test
    public void createPassword() {
        // 你的邮箱密码
        String password = "xgqvsmbvfimnbgjg";
        // 加密后的密码(注意：配置上去的时候需要加 ENC(加密密码))
        String encryptPassword = encryptor.encrypt(password);
        String decryptPassword = encryptor.decrypt(encryptPassword);
        // 打印
        System.out.println("password = " + password);
        System.out.println("encryptPassword = " + encryptPassword);
        System.out.println("decryptPassword = " + decryptPassword);
    }

}

    