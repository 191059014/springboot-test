package com.hb.test.springsecurity.jwt.config;

import com.alibaba.fastjson.JSON;
import com.hb.test.springsecurity.jwt.common.Result;
import com.hb.test.springsecurity.jwt.common.ResultCode;
import com.hb.test.springsecurity.jwt.util.ServletUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足处理器
 *
 * @version v0.1, 2021/4/18 0:03, create by huangbiao.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
        throws IOException, ServletException {
        System.out.println("进入权限不足处理器");
        e.printStackTrace();
        ServletUtils.writeResponse(response, JSON.toJSONString(Result.of(ResultCode.ACCESS_DENIED)));
    }

}
