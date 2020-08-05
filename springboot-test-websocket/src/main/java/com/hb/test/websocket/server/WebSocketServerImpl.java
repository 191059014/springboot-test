package com.hb.test.websocket.server;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * 客户端
 *
 * @version v0.1, 2020/8/5 13:53, create by huangbiao.
 */
@ServerEndpoint("/test/{clientId}/{topic}")
@Component
public class WebSocketServerImpl extends AbstractWebSocketServer implements IWebSocketServer, InitializingBean {

    private static TestService testService;

    @Autowired
    public void setTestService(TestService testService) {
        WebSocketServerImpl.testService = testService;
    }

    @Override
    protected void doAfterOpen(Session session, String clientId, String topic) {
        System.out.println("doAfterOpen");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化WebSocketServerImpl：" + testService);
    }
}

    