package com.hb.test.springsecurity.controller;

import com.hb.test.springsecurity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author Mr.Huang
 * @version v0.1, HelloController.java, 2020/6/1 14:27, create by huangbiao.
 */
@RestController
public class HelloController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String all() {
        return "hello";
    }

    /**
     * 登陆接口
     *
     * @param user 用户信息
     * @return 结果
     */
    @PostMapping("/doLogin")
    public String doLogin(@RequestBody User user) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            System.out.println(111);
        } catch (LockedException e) {
            e.printStackTrace();
            return "用户被锁定";
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return "用户名或密码错误";
        }
        return "login success";
    }

}

    