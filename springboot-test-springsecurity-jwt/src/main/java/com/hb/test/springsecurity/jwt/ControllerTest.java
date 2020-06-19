package com.hb.test.springsecurity.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * 测试
 *
 * @author Mr.Huang
 * @version v0.1, ControllerTest.java, 2020/6/19 9:54, create by huangbiao.
 */
@RestController
public class ControllerTest {

    @Value("${server.port}")
    private String val;

    @Value("${logging.path}")
    private String val1;

    @PostConstruct
    public void init() {
        System.out.println(val);
        System.out.println(val1);
    }

}

    