package com.hb.test.websocket.controller;

import com.hb.test.websocket.server.WebSocketServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
        try {
            WebSocketServer.sendInfo(message, toUserId);
            return "push success";
        } catch (IOException e) {
            e.printStackTrace();
            return "push fail";
        }
    }

}
