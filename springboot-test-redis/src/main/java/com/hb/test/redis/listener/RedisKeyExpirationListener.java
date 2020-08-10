package com.hb.test.redis.listener;

import com.hb.test.redis.container.SpringUtil;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * redis的key过期事件监听，默认匹配规则是：__keyevent@*__:expired
 *
 * @version v0.1, 2020/8/7 11:31, create by huangbiao.
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String redisKey = message.toString();
        System.out.println("channel：" + new String(message.getChannel()) + ", key：" + redisKey + "，value：" + SpringUtil.getBean(StringRedisTemplate.class).opsForValue().get("name") + "，pattern：" + new String(pattern));
    }
}

    