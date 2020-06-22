package com.hb.test;

import com.hb.test.email.SpringbootTestEmailApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试启动类
 *
 * @author Mr.Huang
 * @version v0.1, SpringbootTestEmailApplicationTest.java, 2020/6/22 9:43, create by huangbiao.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootTestEmailApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringbootTestEmailApplicationTest {

    @Before
    public void init() {
        System.out.println("----------开始测试----------");
    }

    @After
    public void after() {
        System.out.println("----------测试结束----------");
    }

}

    