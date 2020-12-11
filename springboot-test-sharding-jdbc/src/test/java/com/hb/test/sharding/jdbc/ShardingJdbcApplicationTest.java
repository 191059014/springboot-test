package com.hb.test.sharding.jdbc;

import static org.junit.Assert.assertTrue;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple ShardingJdbcApplication.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingJdbcApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShardingJdbcApplicationTest {

    @Autowired
    StringEncryptor stringEncryptor;

    /**
     * Rigorous Test :-)
     */
    @Test
    public void encrypt() {
        System.out.println(stringEncryptor.encrypt("root3306"));
    }

}
