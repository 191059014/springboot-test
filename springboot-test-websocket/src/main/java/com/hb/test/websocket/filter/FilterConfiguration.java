package com.hb.test.websocket.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version v0.1, 2020/8/6 16:03, create by huangbiao.
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean sessionByTokenFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 注入过滤器
        registration.setFilter(new WebSocketFilter());
        // 过滤掉所有请求
        registration.addUrlPatterns("/*");
        // 过滤器名称
        registration.setName("webSocketFilter");
        // 是否自动注册 false 取消Filter的自动注册
        registration.setEnabled(true);
        // 过滤器顺序
        registration.setOrder(1);
        System.out.println("初始化webSocketFilter过滤器成功");
        return registration;
    }

}

    