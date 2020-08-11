package com.hb.test.websocket.stomp.controller;

import com.hb.test.websocket.stomp.model.Shout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 任何地方发送消息
 *
 * @version v0.1, 2020/8/11 9:51, create by huangbiao.
 */
@RestController
@Slf4j
public class SendMessageAnywhereController {

    /**
     * 消息发送工具
     */
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    /**
     * 广播消息，不指定用户，所有订阅此的用户都能收到消息
     *
     * @param shout 消息
     */
    @MessageMapping("/broadcastShout")
    public void broadcast(Shout shout) {
        simpMessageSendingOperations.convertAndSend("/topic/shouts", shout);
    }

    /**
     * 注解@SendToUser 表示要将消息发送给指定的用户，会自动在消息目的地前补上"/user"前缀。
     * 如下，最后消息会被发布在  /user/queue/notifications-username。但是问题来了，这个username是怎么来的呢？就是通过 principal 参数来获得的。
     * 那么，principal 参数又是怎么来的呢？需要在spring-websocket 的配置类中重写 configureClientInboundChannel 方法，添加上用户的认证。
     *
     * @param principal 用户
     * @param shout     消息
     * @return 消息
     */
    @MessageMapping("/shout")
    @SendToUser("/queue/notifications")
    public Shout userStomp(Principal principal, Shout shout) {
        String name = principal.getName();
        String message = shout.getMessage();
        log.info("shout认证的名字是：{}，收到的消息是：{}", name, message);
        return shout;
    }

    /**
     * 除了convertAndSend()以外，SimpMessageSendingOperations 还提供了convertAndSendToUser()方法。
     * 按照名字就可以判断出来，convertAndSendToUser()方法能够让我们给特定用户发送消息。
     *
     * @param shout               消息
     * @param stompHeaderAccessor 用户信息
     */
    @MessageMapping("/singleShout")
    public void singleUser(Shout shout, StompHeaderAccessor stompHeaderAccessor) {
        String message = shout.getMessage();
        log.info("singleShout接收到消息：" + message);
        Principal user = stompHeaderAccessor.getUser();
        simpMessageSendingOperations.convertAndSendToUser(user.getName(), "/queue/shouts", shout);
    }

}

    