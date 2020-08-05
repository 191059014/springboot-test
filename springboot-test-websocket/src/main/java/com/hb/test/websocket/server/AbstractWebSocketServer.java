package com.hb.test.websocket.server;

import com.alibaba.fastjson.JSON;
import com.hb.test.websocket.model.Message;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * websocket抽象类
 *
 * @version v0.1, 2020/8/5 13:38, create by huangbiao.
 */
public abstract class AbstractWebSocketServer implements IWebSocketServer {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TestService testService;

    /**
     * 会话集合
     */
    protected static ConcurrentHashMap<String, Session> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    @OnOpen
    @Override
    public void onOpen(Session session, @PathParam("clientId") String clientId) {
        webSocketMap.put(clientId, session);
//        doAfterOpen();
        System.out.println(clientId + "已上线");
    }

    @OnClose
    @Override
    public void onClose(Session session) {
//        webSocketMap.remove(clientId);
        AtomicReference<String> removeKey = new AtomicReference<>("");
        webSocketMap.forEach((key, value) -> {
            if (session.equals(value)) {
                removeKey.set(key);
            }
        });
        webSocketMap.remove(removeKey.get());
        System.out.println(removeKey.get() + "退出了");
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
        try {
            Session toSession = webSocketMap.get(msg.getTo());
            if (toSession == null) {
                System.out.println("信息接收者不存在");
                return;
            }
            Object content = msg.getContent();
            toSession.getBasicRemote().sendText(content == null ? "" : content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实现服务器主动推送
     */
    @Override
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

}

    