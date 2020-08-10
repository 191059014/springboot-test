package com.hb.test.redis.listener;

import org.springframework.data.redis.core.RedisKeyspaceEvent;

/**
 * 事件
 *
 * @version v0.1, 2020/8/10 16:14, create by huangbiao.
 */
public class RedisKeyChangeEvent extends RedisKeyspaceEvent {

    public RedisKeyChangeEvent(byte[] key) {
        super(key);
    }

    public RedisKeyChangeEvent(String channel, byte[] key) {
        super(channel, key);
    }

}

    