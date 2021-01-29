package com.hb.test.codegenerate.exception;

import lombok.Data;

/**
 * 业务异常
 *
 * @version v0.1, 2021/1/27 13:36, create by huangbiao.
 */
@Data
public class BusinessException extends RuntimeException {

    /**
     * 错误标识
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

}
