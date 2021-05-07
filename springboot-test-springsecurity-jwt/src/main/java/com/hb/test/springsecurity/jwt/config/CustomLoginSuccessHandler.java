package com.hb.test.springsecurity.jwt.config;

import com.alibaba.fastjson.JSON;
import com.hb.test.springsecurity.jwt.common.Result;
import com.hb.test.springsecurity.jwt.common.ResultCode;
import com.hb.test.springsecurity.jwt.model.User;
import com.hb.test.springsecurity.jwt.util.JwtUtils;
import com.hb.test.springsecurity.jwt.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆成功处理器
 *
 * @author Mr.Huang
 * @version v0.1, 2020/6/2 9:10, create by huangbiao.
 */
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {

        System.out.println("进入登陆成功处理器");

        User user = (User)authentication.getPrincipal();

        String token = jwtUtils.createToken(user, Boolean.parseBoolean(request.getParameter("rememberMe")));

        ServletUtils.writeResponse(response, JSON.toJSONString(Result.of(ResultCode.SUCCESS, token)));
    }

}
