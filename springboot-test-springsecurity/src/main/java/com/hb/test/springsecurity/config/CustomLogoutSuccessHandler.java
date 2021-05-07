package com.hb.test.springsecurity.config;

import com.alibaba.fastjson.JSON;
import com.hb.test.springsecurity.common.Result;
import com.hb.test.springsecurity.common.ResultCode;
import com.hb.test.springsecurity.util.ServletUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注销成功处理器
 *
 * @author Mr.Huang
 * @version v0.1, 2020/6/2 9:10, create by huangbiao.
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {

        System.out.println("进入注销成功处理器");
        ServletUtils.writeResponse(response, JSON.toJSONString(Result.of(ResultCode.SUCCESS)));

    }
}
