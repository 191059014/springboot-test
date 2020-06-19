package com.hb.test.springsecurity.jwt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 自定义配置
 *
 * @author Mr.Huang
 * @version v0.1, CustomConfig.java, 2020/6/1 14:40, create by huangbiao.
 */
@Configuration
@ConfigurationProperties(prefix = "custom.config")
@Data
public class CustomConfig {
    /**
     * 不需要拦截的地址
     */
    private IgnoreConfig ignores;

    @PostConstruct
    public void init() {
        System.out.println(this);
    }

}
