package com.hb.test.rocketmq.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息模型
 *
 * @version v0.1, 2020/8/14 13:59, create by huangbiao.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageModel implements Serializable {

    private static final long serialVersionUID = -1305179994564809120L;
    // 消息ID
    private String messageId;
    // 消息内容
    private String messageContent;

}

    