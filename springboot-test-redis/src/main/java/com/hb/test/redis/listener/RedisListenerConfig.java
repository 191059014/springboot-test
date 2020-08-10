package com.hb.test.redis.listener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * redis监听
 *
 * @version v0.1, 2020/8/7 11:30, create by huangbiao.
 */
@Configuration
public class RedisListenerConfig {

    /**
     * 创建监听容器
     *
     * @see RedisKeyChangeListener
     * @see RedisKeyChangeEvent
     * @see RedisKeyExpirationListener
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

}

    