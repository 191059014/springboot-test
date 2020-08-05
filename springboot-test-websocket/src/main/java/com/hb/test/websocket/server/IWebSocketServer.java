package com.hb.test.websocket.server;

import javax.websocket.Session;
import java.io.IOException;

/**
 * websocket标准接口
 *
 * @version v0.1, 2020/8/5 13:42, create by huangbiao.
 */
public interface IWebSocketServer {

    /**
     * 打开一个连接的时候
     *
     * @param session  会话
     * @param clientId 客户端标识
     */
    void onOpen(Session session, String clientId);

    /**
     * 连接关闭的时候
     */
    void onClose(Session session);

    /**
     * 连接发生错误的时候
     *
     * @param session 会话
     * @param error   错误
     */
    void onError(Session session, Throwable error);

    /**
     * 接收到消息
     *
     * @param message 消息
     * @param session 会话
     */
    void onMessage(String message, Session session);

    void sendMessage(String message) throws IOException;
}

    