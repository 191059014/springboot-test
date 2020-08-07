package com.hb.test.websocket.server;

import com.alibaba.fastjson.JSON;
import com.hb.test.websocket.model.FromMessage;
import com.hb.test.websocket.util.WebsocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket抽象类
 *
 * @version v0.1, 2020/8/5 13:38, create by huangbiao.
 */
public abstract class AbstractWebsocketServer {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWebsocketServer.class);

    /**
     * 会话集合
     */
    protected static ConcurrentHashMap<String, Session> webSocketMap = new ConcurrentHashMap<>(256);

    /**
     * 获取会话集合
     */
    public static ConcurrentHashMap<String, Session> getWebSocketMap() {
        return webSocketMap;
    }

    @OnOpen
    public void onOpen(Session session) {
        // 将会话放入内存
        webSocketMap.put(WebsocketUtils.getSessionKey(session), session);
        // 建立连接后的操作
        doAfterOpen(session);
    }

    @OnClose
    public void onClose(Session session) {
        // 删除内存中的会话
        webSocketMap.remove(WebsocketUtils.getSessionKey(session));
        // 连接断开后的操作
        doAfterClose(session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        // 删除内存中的会话
        webSocketMap.remove(WebsocketUtils.getSessionKey(session));
        // 连接发生异常后的操作
        doAfterError(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        FromMessage fromMessage = null;
        try {
            fromMessage = JSON.parseObject(message, FromMessage.class);
        } catch (Exception e) {
            LOGGER.error("解析websocket消息失败");
        }
        // 接收到消息后的操作
        doAfterOnMessage(fromMessage, session);
    }

    /**
     * 建立连接后的操作
     *
     * @param session 会话信息
     */
    protected abstract void doAfterOpen(Session session);

    /**
     * 连接断开后的操作
     *
     * @param session 会话信息
     */
    protected abstract void doAfterClose(Session session);

    /**
     * 连接发生异常后的操作
     *
     * @param session 会话信息
     */
    protected abstract void doAfterError(Session session);

    /**
     * 接收到消息后的操作
     *
     * @param fromMessage 会话信息
     */
    protected abstract void doAfterOnMessage(FromMessage fromMessage, Session session);

}

    