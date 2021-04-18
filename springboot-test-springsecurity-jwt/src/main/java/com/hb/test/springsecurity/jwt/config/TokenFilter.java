package com.hb.test.springsecurity.jwt.config;

import com.alibaba.fastjson.JSON;
import com.hb.test.springsecurity.jwt.common.RedisKeyFactory;
import com.hb.test.springsecurity.jwt.common.Result;
import com.hb.test.springsecurity.jwt.common.ResultCode;
import com.hb.test.springsecurity.jwt.exception.BusinessException;
import com.hb.test.springsecurity.jwt.model.User;
import com.hb.test.springsecurity.jwt.util.JwtUtils;
import com.hb.test.springsecurity.jwt.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 字符串类型的redis操作类
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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

        String userId = null;

        try {
            userId = jwtUtils.getUserId(request);
        } catch (BusinessException e) {
            ServletUtils.writeResponse(response, JSON.toJSONString(Result.of(e.getKey(), e.getMsg())));
            return;
        }

        String userCache = stringRedisTemplate.opsForValue().get(RedisKeyFactory.getJwtKey(userId));
        if (StringUtils.isEmpty(userCache)) {
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
