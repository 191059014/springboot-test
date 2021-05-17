package com.hb.test.actuator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * desc
 *
 * @version v0.1, 2021/5/17 17:06, create by huangbiao.
 */
@RestController
@RequestMapping("/controller/test")
public class TestController {

    @GetMapping("method1")
    public Object method1() {
        return this.getClass().getName();
    }

}
