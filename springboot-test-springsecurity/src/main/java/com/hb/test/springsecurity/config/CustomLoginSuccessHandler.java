package com.hb.test.springsecurity.config;

import com.alibaba.fastjson.JSON;
import com.hb.test.springsecurity.common.Result;
import com.hb.test.springsecurity.common.ResultCode;
import com.hb.test.springsecurity.model.User;
import com.hb.test.springsecurity.util.RedisMock;
import com.hb.test.springsecurity.util.ServletUtils;
import com.hb.test.springsecurity.util.TokenMock;
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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {

        System.out.println("进入登陆成功处理器");

        User user = (User)authentication.getPrincipal();

        String token = TokenMock.getToken(user.getUsername());

        RedisMock.set(token, JSON.toJSONString(user));
        System.out.println("将用户信息放入redis，token=" + token + "，用户信息=" + JSON.toJSONString(user));

        ServletUtils.writeResponse(response, JSON.toJSONString(Result.of(ResultCode.SUCCESS, token)));
    }

}
