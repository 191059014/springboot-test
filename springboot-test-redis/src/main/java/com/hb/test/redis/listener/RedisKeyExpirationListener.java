package com.hb.test.redis.listener;

import com.hb.test.redis.container.SpringUtil;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * redis过期事件监听
 *
 * @version v0.1, 2020/8/7 11:31, create by huangbiao.
 */
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
        System.out.println("RedisKeyExpirationListener: " + listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String redisKey = message.toString();
        System.out.println("key：" + redisKey + "，pattern：" + new String(pattern));
        System.out.println("value：" + SpringUtil.getBean(StringRedisTemplate.class).opsForValue().get("testKey"));
    }
}

    