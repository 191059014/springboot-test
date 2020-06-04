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
        System.out.println("goto home");
        return "home";
    }

    @GetMapping("/toLogin")
    public String toLogin() {
        System.out.println("goto login");
        return "login";
    }

    @GetMapping("/403")
    public String error_403() {
        System.out.println("goto 403");
        return "403";
    }

    @GetMapping("/v1")
    public String v1() {
        System.out.println("goto v1");
        return "v1";
    }

    @GetMapping("/v2")
    public String v2() {
        System.out.println("goto v2");
        return "v2";
    }

}

    