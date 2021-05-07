package com.hb.test.springsecurity.jwt.exception;

import com.hb.test.springsecurity.jwt.common.ResultCode;

/**
 * 公共业务异常类
 *
 * @version v0.1, 2021/1/27 13:36, create by huangbiao.
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误标识
     */
    private String key;

    /**
     * 错误信息
     */
    private String msg;

    public BusinessException(ResultCode resultCode) {
        this.key = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public String getKey() {
        return key;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "BusinessException{" + "key='" + key + '\'' + ", msg='" + msg + '\'' + "} " + super.toString();
    }
}
