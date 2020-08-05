package com.hb.test.websocket.util;

import javax.websocket.Session;
import java.io.IOException;

/**
 * 工具类
 *
 * @version v0.1, 2020/8/5 18:23, create by huangbiao.
 */
public class WebSocketUtils {

    /**
     * 发送消息
     *
     * @param session 接收者的会话session
     * @param message 消息内容
     * @return true为发送成功
     */
    public static boolean sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}

    