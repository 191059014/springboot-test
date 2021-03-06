package com.hb.test.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接收消息内容
 *
 * @version v0.1, 2020/8/7 15:39, create by huangbiao.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FromMessage {

    // 发送给谁
    private String to;

    // 主题
    private String topic;

    // 消息内容
    private String content;

}

    