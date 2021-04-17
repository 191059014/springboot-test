package com.hb.test.springsecurity.config;

import com.alibaba.fastjson.JSON;
import com.hb.test.springsecurity.common.Result;
import com.hb.test.springsecurity.common.ResultCode;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注销处理器
 *
 * @author Mr.Huang
 * @version v0.1, 2020/6/2 9:10, create by huangbiao.
 */
@Component
public class CustomLogoutHandler implements LogoutHandler {

    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        System.out.println("进入登陆成功处理器");
        response.getWriter().write(JSON.toJSONString(Result.of(ResultCode.SUCCESS)));
    }

}
