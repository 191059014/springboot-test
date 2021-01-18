package com.hb.test.docker.controller;

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

    @GetMapping("/testDocker")
    public Object testDocker() {
        return "this is docker server";
    }

}
