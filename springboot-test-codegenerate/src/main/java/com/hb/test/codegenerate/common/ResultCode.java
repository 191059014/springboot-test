package com.hb.test.codegenerate.common;

import lombok.Getter;

/**
 * 分页
 *
 * @version v0.1, 2021/1/27 13:36, create by huangbiao.
 */
@Getter
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(10000, "成功"),
    /**
     * 失败
     */
    ERROR(50000, "失败");

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
