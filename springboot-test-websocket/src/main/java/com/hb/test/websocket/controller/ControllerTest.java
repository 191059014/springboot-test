package com.hb.test.websocket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @version v0.1, 2020/8/4 20:03, create by huangbiao.
 */
@RestController
public class ControllerTest {

    @GetMapping("/test")
    public String test() {
        return "success";
    }

    @GetMapping("/push/{toUserId}")
    public String pushToWeb(@RequestParam("message") String message, @PathVariable("toUserId") String toUserId) {
        return "";
    }

}
