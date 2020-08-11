package com.hb.test.websocket.stomp.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息
 *
 * @version v0.1, 2020/8/11 9:41, create by huangbiao.
 */
@Data
public class Shout implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = -7939616198038452346L;
    // 消息
    private String message;
    // 接收者
    private String to;

}

    