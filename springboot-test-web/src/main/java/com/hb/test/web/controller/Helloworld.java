package com.hb.test.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world
 *
 * @version v0.1, 2021/1/29 15:30, create by huangbiao.
 */
@RestController
public class Helloworld {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Helloworld.class);

    @GetMapping("/sayHello")
    public Object sayHello() {
        String msg = "hello world";
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("{}", msg);
        }
        return msg;
    }

}
