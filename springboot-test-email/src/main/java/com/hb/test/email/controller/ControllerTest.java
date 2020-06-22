package com.hb.test.email.controller;

import com.hb.test.email.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author Mr.Huang
 * @version v0.1, ControllerTest.java, 2020/6/22 9:32, create by huangbiao.
 */
@RestController
public class ControllerTest {

    @Autowired
    MailService mailService;

    @GetMapping("/sendSimpleMail")
    public void sendSimpleMail() {
        String to = "191059014@qq.com";
        String subject = "自测subject";
        String content = "这是一封自己发给自己的邮件";
        String[] cc = new String[]{"1352783642@qq.com"}; // 抄送
        mailService.sendSimpleMail(to, subject, content, cc);
    }

}

    