package com.hb.test.springsecurity.config;

import com.alibaba.fastjson.JSON;
import com.hb.test.springsecurity.common.Result;
import com.hb.test.springsecurity.common.ResultCode;
import com.hb.test.springsecurity.util.RedisMock;
import com.hb.test.springsecurity.util.ServletUtils;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        System.out.println("进入注销处理器");
        // 清空上下文
        SecurityContextHolder.clearContext();
        System.out.println("清除spring security上下文");

        String token = request.getHeader("token");
        RedisMock.remove(token);

        System.out.println("清除redis=" + token);
    }

}
