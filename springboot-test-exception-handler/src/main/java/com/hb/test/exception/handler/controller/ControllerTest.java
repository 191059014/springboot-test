package com.hb.test.exception.handler.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author Mr.Huang
 * @version v0.1, ControllerTest.java, 2020/6/22 14:30, create by huangbiao.
 */
@RestController
public class ControllerTest {

    @GetMapping("/errorToJson")
    public String errorToJson() {
        if (true) {
            throw new IllegalStateException("com.hb.test.exception.handler.controller.ControllerTest.errorToJson状态异常");
        }
        return "com.hb.test.exception.handler.controller.ControllerTest.errorToJson";
    }

    @GetMapping("/errorToPage")
    public String errorToPage() {
        if (true) {
            throw new IllegalArgumentException("com.hb.test.exception.handler.controller.ControllerTest.errorToPage参数错误");
        }
        return "com.hb.test.exception.handler.controller.ControllerTest.errorToPage";
    }

}

    