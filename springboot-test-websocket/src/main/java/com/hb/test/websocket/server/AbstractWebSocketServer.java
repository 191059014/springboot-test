package com.hb.test.websocket.server;

import com.alibaba.fastjson.JSON;
import com.hb.test.websocket.model.Message;
import com.hb.test.websocket.util.WebSocketUtils;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket抽象类
 *
 * @version v0.1, 2020/8/5 13:38, create by huangbiao.
 */
public abstract class AbstractWebSocketServer implements IWebSocketServer {

    /**
     * 会话集合
     */
    protected static ConcurrentHashMap<String, Session> webSocketMap = new ConcurrentHashMap<>();

    @OnOpen
    @Override
    public void onOpen(Session session, @PathParam("clientId") String clientId, @PathParam("topic") String topic) {
        webSocketMap.put(clientId, session);
        doAfterOpen(session, clientId, topic);
        System.out.println(clientId + "已上线");
    }

    protected abstract void doAfterOpen(Session session, String clientId, String topic);

    @OnClose
    @Override
    public void onClose(Session session) {
        String clientId = session.getPathParameters().get("clientId");
        String topic = session.getPathParameters().get("topic");
        webSocketMap.remove(clientId);
        System.out.println(clientId + "退出了");
    }

    @OnError
    @Override
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误了：" + error);
    }

    @OnMessage
    @Override
    public void onMessage(String message, Session session) {
        System.out.println("接收消息：" + message);
        Message msg = JSON.parseObject(message, Message.class);
        Session toSession = webSocketMap.get(msg.getTo());
        if (toSession == null) {
            System.out.println("信息接收者不存在");
            return;
        }
        Object content = msg.getContent();
        boolean sendResult = WebSocketUtils.sendMessage(toSession, content == null ? "" : content.toString());
        if (sendResult) {
            System.out.println("发送消息成功：" + sendResult);
        } else {
            System.out.println("发送消息失败：" + sendResult);
        }
    }

}

    