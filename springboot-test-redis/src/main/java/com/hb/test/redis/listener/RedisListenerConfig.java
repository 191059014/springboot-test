package com.hb.test.redis.listener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * redis监听
 *
 * @version v0.1, 2020/8/7 11:30, create by huangbiao.
 */
@Configuration
public class RedisListenerConfig {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(new RedisKeyExpirationListener(container), new PatternTopic("__keyevent@0__:expired"));
//        container.addMessageListener(new RedisKeyExpirationListener(container), new PatternTopic("__key*__:*"));
        container.addMessageListener(new RedisKeyExpirationListener(container), new PatternTopic("__keyevent@0__:SETNX"));
//        container.addMessageListener(new RedisKeyExpirationListener(container), new PatternTopic("__keyspace@0__:set"));
        System.out.println("RedisListenerConfig: " + container);
        return container;
    }

}

    