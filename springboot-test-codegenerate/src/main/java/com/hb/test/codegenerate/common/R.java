package com.hb.test.codegenerate.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 公共响应类
 *
 * @version v0.1, 2021/1/27 13:36, create by huangbiao.
 */
@Data
@AllArgsConstructor
public class R<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

}
