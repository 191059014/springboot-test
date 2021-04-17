package com.hb.test.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 登陆
 *
 * @author Mr.Huang
 * @version v0.1, LoginController.java, 2020/6/1 14:27, create by huangbiao.
 */
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 登陆接口
     *
     * @return 结果
     */
    @RequestMapping(value = "/login")
    public String doLogin(HttpServletRequest request) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getParameter("userName"), request.getParameter("password")));
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
