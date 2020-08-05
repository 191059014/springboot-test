package com.hb.test.websocket.server;

import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

/**
 * 客户端
 *
 * @version v0.1, 2020/8/5 13:53, create by huangbiao.
 */
@ServerEndpoint("/test/{clientId}")
@Component
public class WebSocketServerImpl extends AbstractWebSocketServer implements IWebSocketServer {



}

    