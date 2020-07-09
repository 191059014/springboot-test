package com.hb.test.log.support.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * traceId的http处理过滤器
 *
 * @version v0.1, 2020/7/9 14:34, create by huangbiao.
 */
public class TraceIdHttpFilter implements Filter {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TraceIdHttpFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String traceId = httpServletRequest.getHeader("traceId");
        if (traceId == null) {
            traceId = UUID.randomUUID().toString().replaceAll("-", "");
            LOGGER.info("http请求头里没有traceId，生成traceId：{}", traceId);
        }
        MDC.put("traceId", traceId);
        LOGGER.info("设置traceId：{}", traceId);
        filterChain.doFilter(servletRequest, servletResponse);
    }

}

    