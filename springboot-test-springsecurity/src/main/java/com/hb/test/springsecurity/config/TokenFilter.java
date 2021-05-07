package com.hb.test.springsecurity.config;

import com.alibaba.fastjson.JSON;
import com.hb.test.springsecurity.common.Result;
import com.hb.test.springsecurity.common.ResultCode;
import com.hb.test.springsecurity.model.User;
import com.hb.test.springsecurity.util.RedisMock;
import com.hb.test.springsecurity.util.ServletUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器
 *
 * @version v0.1, 2021/4/18 1:33, create by huangbiao.
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Value("${security.ignoreUrls}")
    private String ignoreUrls;

    @Value("${security.loginUrl}")
    private String loginUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        System.out.println("进入TokenFilter：" + request.getMethod() + "===" + request.getRequestURI());
        /*
         * 忽略的请求直接通过，不需要验证token
         */
        boolean isIgnoreRequest = false;
        String[] ignoreUrlArr = ignoreUrls.split(",");
        for (String ignoreUrl : ignoreUrlArr) {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignoreUrl);
            if (matcher.matches(request)) {
                isIgnoreRequest = true;
                break;
            }
        }
        if (isIgnoreRequest || loginUrl.equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        /*
         * 验证token
         */
        System.out.println("进入Token验证");
        String token = request.getHeader("token");
        if ("".equals(token) || token == null) {
            ServletUtils.writeResponse(response, JSON.toJSONString(Result.of(ResultCode.TOKEN_IS_NULL)));
            return;
        }

        String userCache = RedisMock.get(token);
        if (userCache == null) {
            ServletUtils.writeResponse(response, JSON.toJSONString(Result.of(ResultCode.NOT_LOGIN)));
            return;
        }

        User user = JSON.parseObject(userCache, User.class);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 设置认证上下文，如果不设置，则视为匿名访问，会进入CustomAuthenticationEntryPoint处理器
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 放行
        filterChain.doFilter(request, response);
    }

}
