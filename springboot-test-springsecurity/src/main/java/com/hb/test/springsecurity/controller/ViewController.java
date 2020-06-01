package com.hb.test.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 视图controller
 *
 * @author Mr.Huang
 * @version v0.1, ViewController.java, 2020/6/1 14:47, create by huangbiao.
 */
@Controller
public class ViewController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/ignore")
    public String ignore() {
        return "ignore";
    }

}

    