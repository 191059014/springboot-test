package com.hb.test.websocket.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息
 *
 * @version v0.1, 2020/8/5 13:33, create by huangbiao.
 */
@Data
public class Message implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = 2249875567393564117L;
    // 发送者
    private String from;
    // 接收者
    private String to;
    // 消息主题
    private String topic;
    // 消息内容
    private String content;

}

    