package com.hb.test.springsecurity.jwt.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回前端数据模型
 *
 * @version v0.1, 2021/4/17 23:38, create by huangbiao.
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 3730853535572002727L;

    private String code;
    private String msg;
    private T obj;

    public Result(String code, String msg, T obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public static <T> Result<T> of(String code, String msg, T obj) {
        return new Result<>(code, msg, obj);
    }

    public static <T> Result<T> of(String code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> of(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMsg(), null);
    }

    public static <T> Result<T> of(ResultCode resultCode, T obj) {
        return new Result<>(resultCode.getCode(), resultCode.getMsg(), obj);
    }

}
