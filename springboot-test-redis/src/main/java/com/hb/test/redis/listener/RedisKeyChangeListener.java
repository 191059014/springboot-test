package com.hb.test.redis.listener;

import com.hb.test.redis.container.SpringUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.KeyspaceEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * redis值改变的监听
 *
 * @version v0.1, 2020/8/10 16:10, create by huangbiao.
 */
@Component
public class RedisKeyChangeListener extends KeyspaceEventMessageListener implements ApplicationEventPublisherAware {

    /**
     * 键事件通知，需要修改redis配置文件项：notify-keyspace-events Ex
     */
    private static final Topic KEYEVENT_CHANGE_TOPIC = new PatternTopic("__keyevent@0__:set");// 监控所有key的set操作，0代表0库
    /**
     * 键空间通知，需要修改redis配置文件项：notify-keyspace-events K$
     */
    private static final Topic KEYSPACE_CHANGE_TOPIC = new PatternTopic("__keyspace@*__:name");// 监控所有key=“name”的变化，*代表所有库
    /**
     * 监控所有通知，包含事件和空间
     */
    private static final Topic TOPIC = new PatternTopic("__key*__:*");

    private ApplicationEventPublisher publisher;

    public RedisKeyChangeListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
        System.out.println("初始化RedisKeyChangeListener");
    }

    @Override
    protected void doRegister(RedisMessageListenerContainer container) {
        container.addMessageListener(this, Arrays.asList(TOPIC));
        System.out.println("doRegister：" + KEYEVENT_CHANGE_TOPIC);
    }

    @Override
    protected void doHandleMessage(Message message) {
        this.publisher.publishEvent(new RedisKeyChangeEvent(message.getBody()));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String redisKey = message.toString();
        System.out.println("channel：" + new String(message.getChannel()) + ", key：" + redisKey + "，value：" + SpringUtil.getBean(StringRedisTemplate.class).opsForValue().get("name") + "，pattern：" + new String(pattern));
    }
}

    