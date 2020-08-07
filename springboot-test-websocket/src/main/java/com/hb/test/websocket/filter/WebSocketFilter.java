package com.hb.test.websocket.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * websocket过滤器，可做鉴权
 *
 * @version v0.1, 2020/8/6 16:04, create by huangbiao.
 */
public class WebSocketFilter implements Filter {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("通过websocket过滤器");
        filterChain.doFilter(servletRequest, servletResponse);
    }

}

    