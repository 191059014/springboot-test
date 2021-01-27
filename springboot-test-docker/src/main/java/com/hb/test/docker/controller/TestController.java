package com.hb.test.docker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @version v0.1, 2021/1/15 14:16, create by huangbiao.
 */
@RestController
@RequestMapping("/controller/test")
public class TestController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private Environment env;

    @GetMapping("/testDocker")
    public Object testDocker() {
        return "this is docker server";
    }

    @GetMapping("/print/environment")
    public Object printEnvironment() {
        String profilesActive = env.getProperty("spring.profiles.active");
        LOGGER.info("profilesActive={}", profilesActive);
        return profilesActive;
    }

}
