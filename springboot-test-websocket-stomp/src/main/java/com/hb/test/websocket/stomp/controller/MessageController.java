package com.hb.test.websocket.stomp.controller;

import com.hb.test.websocket.stomp.model.Shout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息控制器
 * <p>
 * 使用 @MessageMapping 或者 @SubscribeMapping 注解可以处理客户端发送过来的消息，并选择方法是否有返回值。
 * <p>
 * 如果 @MessageMapping 注解的控制器方法有返回值的话，返回值会被发送到消息代理，只不过会添加上"/topic"前缀。可以使用@SendTo 重写消息目的地；
 * <p>
 * 如果 @SubscribeMapping 注解的控制器方法有返回值的话，返回值会直接发送到客户端，不经过代理。如果加上@SendTo 注解的话，则要经过消息代理。
 *
 * @version v0.1, 2020/8/11 9:39, create by huangbiao.
 */
@RestController
@Slf4j
public class MessageController {

    /**
     * 服务端处理客户端发来的STOMP消息，主要用的是 @MessageMapping 注解
     * MessageMapping指定目的地是“/app/marco”（“/app”前缀是隐含的，因为我们将其配置为应用的目的地前缀）
     * 尤其注意，这个处理器方法有一个返回值，这个返回值并不是返回给客户端的，而是转发给消息代理的，如果客户端想要这个返回值的话，只能从消息代理订阅。
     * SendTo 注解重写了消息代理的目的地，如果不指定@SendTo，帧所发往的目的地会与触发处理器方法的目的地相同，只不过会添加上“/topic”前缀。
     *
     * @param shout 消息承载
     * @return 消息
     */
    @MessageMapping("/marco")
    @SendTo("/topic/marco")
    public Shout stompHandle(Shout shout) {
        log.info("接收到消息：" + shout.getMessage());
        Shout s = new Shout();
        s.setMessage("java端消息");
        return s;
    }

    /**
     * 如果客户端就是想要服务端直接返回消息呢？听起来不就是HTTP做的事情！即使这样，STOMP 仍然为这种一次性的响应提供了支持，
     * 用的是@SubscribeMapping注解，与HTTP不同的是，这种请求-响应模式是异步的
     */
    @SubscribeMapping("/getShout")
    public Shout getShout() {
        Shout shout = new Shout();
        shout.setMessage("客户端就是想要服务端直接返回消息，用的是@SubscribeMapping注解");
        return shout;
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
    @SendToUser("/queue/errors")
    public Exception handleExceptions(Exception t) {
        t.printStackTrace();
        return t;
    }

}

    