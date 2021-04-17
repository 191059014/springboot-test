package com.hb.test.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 视图controller
 *
 * @author Mr.Huang
 * @version v0.1, ViewController.java, 2020/6/1 14:47, create by huangbiao.
 */
@RestController
public class TestController {

    @PostMapping("/v1")
    @PreAuthorize("hasAuthority('v1')")
    public String v1() {
        return "成功访问v1";
    }

    @PostMapping("/v2")
    @PreAuthorize("hasAuthority('v2')")
    public String v2() {
        return "成功访问v2";
    }

}
