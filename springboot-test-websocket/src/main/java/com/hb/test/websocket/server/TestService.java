package com.hb.test.websocket.server;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @version v0.1, 2020/8/5 14:25, create by huangbiao.
 */
@Component
public class TestService implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化TestService");
    }
}

    