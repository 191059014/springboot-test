package com.hb.test.springsecurity.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * 测试
 *
 * @author Mr.Huang
 * @version v0.1, LoginController.java, 2020/6/1 14:27, create by huangbiao.
 */
@Controller
public class LoginController {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public String login(@RequestBody Map<String, Object> map) {
        System.out.println("LoginController.login" + map);
        return "/home";
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public String logout(@RequestBody Map<String, Object> map) {
        System.out.println("LoginController.logout" + map);
        return "/toLogin";
    }

}

    