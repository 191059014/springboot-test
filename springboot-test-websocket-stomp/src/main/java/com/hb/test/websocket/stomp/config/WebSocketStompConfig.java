package com.hb.test.websocket.stomp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.security.Principal;

/**
 * stomp配置
 *
 * @version v0.1, 2020/8/11 9:35, create by huangbiao.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp").withSockJS();
    }

    /**
     * 如果不重载它的话，将会自动配置一个简单的内存消息代理，用它来处理以"/topic"为前缀的消息
     *
     * @param registry 消息注册
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //基于内存的STOMP消息代理，所有消息都要包含下面这个配置的地址
        registry.enableSimpleBroker("/queue", "/topic");

//        //基于RabbitMQ 的STOMP消息代理
//        registry.enableStompBrokerRelay("/queue", "/topic")
//                .setRelayHost(host)
//                .setRelayPort(port)
//                .setClientLogin(userName)
//                .setClientPasscode(password);

        registry.setApplicationDestinationPrefixes("/app", "/foo");
        registry.setUserDestinationPrefix("/user");
    }

    /**
     * 1、设置拦截器
     * 2、首次连接的时候，获取其Header信息，利用Header里面的信息进行权限认证
     * 3、通过认证的用户，使用 accessor.setUser(user); 方法，将登陆信息绑定在该 StompHeaderAccessor 上，在Controller方法上可以获取 StompHeaderAccessor 的相关信息
     *
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                //1、判断是否首次连接
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    //2、判断用户名和密码
                    String username = accessor.getNativeHeader("username").get(0);
                    String password = accessor.getNativeHeader("password").get(0);
                    Principal principal = new Principal() {
                        @Override
                        public String getName() {
                            return username;
                        }
                    };
                    accessor.setUser(principal);
                    return message;
                }
                //不是首次连接，已经登陆成功
                return message;
            }

        });
    }
}

    