package com.hb.test.websocket.stomp.controller;

import com.hb.test.websocket.stomp.model.Shout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

/**
 * 聊天controller
 *
 * @version v0.1, 2020/8/11 10:22, create by huangbiao.
 */
@RestController
@Slf4j
public class ChatController {

    /**
     * 消息发送工具
     */
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    /**
     * 注解@SendToUser 表示要将消息发送给指定的用户，会自动在消息目的地前补上"/user"前缀。
     * 如下，最后消息会被发布在  /user/queue/notifications-username。但是问题来了，这个username是怎么来的呢？就是通过 principal 参数来获得的。
     * 那么，principal 参数又是怎么来的呢？需要在spring-websocket 的配置类中重写 configureClientInboundChannel 方法，添加上用户的认证。
     *
     * @param shout 消息
     */
    @MessageMapping("/chat")
    public void chatToOne(Shout shout) {
        String message = shout.getMessage();
        log.info("chatToOne接收到消息：" + message + "，即将发送给：" + shout.getTo());
        simpMessageSendingOperations.convertAndSendToUser(shout.getTo(), "/queue/chat", shout);
    }

    /**
     * 在处理消息的时候，有可能会出错并抛出异常。因为STOMP消息异步的特点，发送者可能永远也不会知道出现了错误。
     * 用@MessageExceptionHandler标注的方法能够处理消息方法中所抛出的异常。我们可以把错误发送给用户特定的目的地上，
     * 然后用户从该目的地上订阅消息，从而用户就能知道自己出现了什么错误啦
     *
     * @param t 异常
     * @return Exception
     */
    @MessageExceptionHandler(Exception.class)
    @SendToUser("/chat/errors")
    public Exception handleExceptions(Exception t) {
        t.printStackTrace();
        return t;
    }

}

    