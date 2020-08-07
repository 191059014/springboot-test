package com.hb.test.websocket.util;

import com.alibaba.fastjson.JSON;
import com.hb.test.websocket.common.Constant;
import com.hb.test.websocket.model.ToMessage;
import com.hb.test.websocket.server.AbstractWebsocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;

/**
 * 工具类
 *
 * @version v0.1, 2020/8/7 15:23, create by huangbiao.
 */
public class WebsocketUtils {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketUtils.class);

    /**
     * 获取存放session的key
     *
     * @return 字符串
     */
    public static String getSessionKey(Session session) {
        return session.getUserProperties().get(Constant.TOKEN).toString();
    }

    /**
     * 会话是否存在
     *
     * @return true为存在
     */
    public static boolean isSessionExist(String sessionKey) {
        return getSession(sessionKey) != null;
    }

    /**
     * 获取session
     *
     * @param sessionKey 会话key
     * @return Session
     */
    public static Session getSession(String sessionKey) {
        return AbstractWebsocketServer.getWebSocketMap().get(sessionKey);
    }

    /**
     * 发送消息
     *
     * @param session   接收者的会话session
     * @param toMessage 消息内容
     * @return true为发送成功
     */
    public static boolean sendMessage(Session session, ToMessage toMessage) {
        try {
            session.getBasicRemote().sendText(JSON.toJSONString(toMessage));
            return true;
        } catch (IOException e) {
            LOGGER.error("发送消息异常：{}", e);
            return false;
        }
    }

}

    