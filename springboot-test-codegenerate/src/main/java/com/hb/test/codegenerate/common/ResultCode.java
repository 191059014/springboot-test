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
     * 数据库连接失败
     */
    DB_CONNECT_ERROR(20001, "数据库连接失败, 请仔细检查连接参数"),
    /**
     * 失败
     */
    ERROR(50000, "系统异常, 请稍后再试");

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
