package com.hb.test.springsecurity.jwt.config;

import com.alibaba.fastjson.JSON;
import com.hb.test.springsecurity.jwt.common.Result;
import com.hb.test.springsecurity.jwt.common.ResultCode;
import com.hb.test.springsecurity.jwt.util.ServletUtils;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆失败处理器
 *
 * @author Mr.Huang
 * @version v0.1, 2020/6/2 9:10, create by huangbiao.
 */
@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException e) throws IOException {
        System.out.println("进入登陆失败处理器");
        e.printStackTrace();
        ResultCode resultCode = null;
        if (e instanceof AccountExpiredException) {
            // 账号过期
            resultCode = ResultCode.ACCOUNT_EXPIRED;
        } else if (e instanceof BadCredentialsException) {
            // 密码错误
            resultCode = ResultCode.PASSWORD_ERROR;
        } else if (e instanceof CredentialsExpiredException) {
            // 密码过期
            resultCode = ResultCode.PASSWORD_EXPIRED;
        } else if (e instanceof DisabledException) {
            // 账号不可用
            resultCode = ResultCode.ACCOUNT_DISABLED;
        } else if (e instanceof LockedException) {
            // 账号锁定
            resultCode = ResultCode.ACCOUNT_LOCKED;
        } else if (e instanceof InternalAuthenticationServiceException) {
            // 用户不存在
            resultCode = ResultCode.ACCOUNT_NOT_EXIST;
        } else {
            // 其他错误
            resultCode = ResultCode.FAIL;
        }

        ServletUtils.writeResponse(response, JSON.toJSONString(Result.of(resultCode)));

    }

}
