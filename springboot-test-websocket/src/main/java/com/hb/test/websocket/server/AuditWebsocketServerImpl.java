package com.hb.test.websocket.server;

import com.alibaba.fastjson.JSON;
import com.hb.test.websocket.common.Constant;
import com.hb.test.websocket.config.CommonServerConfigurator;
import com.hb.test.websocket.model.FromMessage;
import com.hb.test.websocket.model.ToMessage;
import com.hb.test.websocket.util.WebsocketUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * 审核流程websocket服务
 *
 * @version v0.1, 2020/8/5 13:53, create by huangbiao.
 */
@ServerEndpoint(value = "/ws/audit", configurator = CommonServerConfigurator.class)
@Component
public class AuditWebsocketServerImpl extends AbstractWebsocketServer implements InitializingBean {

    @Override
    protected void doAfterOpen(Session session) {
        System.out.println("doAfterOpen，" + session.getUserProperties().get(Constant.TOKEN) + " 已上线");
    }

    @Override
    protected void doAfterClose(Session session) {
        System.out.println("doAfterClose，" + session.getUserProperties().get(Constant.TOKEN) + " 已退出");
    }

    @Override
    protected void doAfterError(Session session) {
        System.out.println("doAfterError，" + session.getUserProperties().get(Constant.TOKEN) + " 异常退出");
    }

    @Override
    protected void doAfterOnMessage(FromMessage fromMessage, Session session) {
        System.out.println("doAfterOnMessage，收到 " + session.getUserProperties().get(Constant.TOKEN) + " 的消息，[" + JSON.toJSONString(fromMessage) + "]");
        Session toSession = WebsocketUtils.getSession(fromMessage.getTo());
        if (toSession == null) {
            System.out.println(fromMessage.getTo() + " 不在线上，无法收到消息");
            return;
        }
        WebsocketUtils.sendMessage(toSession, new ToMessage(session.getUserProperties().get(Constant.TOKEN).toString(), fromMessage.getTopic(), fromMessage.getContent()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        System.out.println("初始化WebSocketServerImpl：" + testService);
    }

}

    