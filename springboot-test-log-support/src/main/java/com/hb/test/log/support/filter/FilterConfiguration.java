package com.hb.test.log.support.filter;

import feign.RequestInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 *
 * @version v0.1, 2020/7/9 14:39, create by huangbiao.
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean sessionByTokenFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 注入过滤器
        registration.setFilter(new TraceIdHttpFilter());
        // 过滤掉所有请求
        registration.addUrlPatterns("/*");
        // 过滤器名称
        registration.setName("sessionByTokenFilter");
        // 是否自动注册 false 取消Filter的自动注册
        registration.setEnabled(true);
        // 过滤器顺序
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public RequestInterceptor registerFeignInterceptor() {
        return new TraceIdFeignInterceptor();
    }

}

    