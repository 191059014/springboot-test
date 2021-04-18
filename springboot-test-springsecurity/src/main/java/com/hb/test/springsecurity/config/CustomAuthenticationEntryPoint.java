package com.hb.test.springsecurity.config;

import com.alibaba.fastjson.JSON;
import com.hb.test.springsecurity.common.Result;
import com.hb.test.springsecurity.common.ResultCode;
import com.hb.test.springsecurity.util.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 匿名用户访问无权限资源时的异常处理器（当从请求里获取不到用户名和密码时会进入此处理器）
 *
 * @version v0.1, 2021/4/18 0:09, create by huangbiao.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
        throws IOException, ServletException {
        System.out.println("匿名用户访问无权限资源时的异常处理器");
        e.printStackTrace();
        ServletUtils.writeResponse(response, JSON.toJSONString(Result.of(ResultCode.ACCESS_DENIED)));
    }

}
