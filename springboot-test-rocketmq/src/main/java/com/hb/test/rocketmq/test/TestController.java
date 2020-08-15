package com.hb.test.rocketmq.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @version v0.1, 2020/8/14 14:19, create by huangbiao.
 */
@RestController
public class TestController {

    @Autowired
    Producer producer;

    /**
     * 发送简单消息
     *
     * @return Object
     */
    @GetMapping("/testSendSimpleMessage")
    public Object testSendSimpleMessage() {
        producer.sendMessage(new MessageModel("messageId_1", "this is message 1"));
        return "success";
    }

}

    