package com.hb.test.springsecurity.jwt.controller;

import com.hb.test.springsecurity.jwt.jwt.JwtUtils;
import com.hb.test.springsecurity.jwt.model.User;
import com.hb.test.springsecurity.jwt.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * 测试
 *
 * @author Mr.Huang
 * @version v0.1, LoginController.java, 2020/6/1 14:27, create by huangbiao.
 */
@RestController
public class LoginController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 登录
     */
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> map) throws IOException {
        System.out.println("LoginController.login" + map);
        String userName = map.get("userName");
        String password = map.get("password");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        String jwt = jwtUtils.createToken(user.getUserId(), user.getUserName(), userPrincipal.getRoleList(), userPrincipal.getAuthorities(), false);
        return jwt;
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public String logout(@RequestBody Map<String, Object> map) {
        System.out.println("LoginController.logout" + map);
        return "success";
    }

}

    