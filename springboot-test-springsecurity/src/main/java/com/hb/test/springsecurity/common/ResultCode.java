package com.hb.test.springsecurity.common;

/**
 * 响应码
 *
 * @version v0.1, 2021/4/17 23:40, create by huangbiao.
 */
public enum ResultCode {

    SUCCESS("10000", "成功"),
    FAIL("20000", "失败"),
    ACCOUNT_EXPIRED("30100", "账号过期"),
    PASSWORD_ERROR("30101", "密码错误"),
    PASSWORD_EXPIRED("30102", "密码过期"),
    ACCOUNT_DISABLED("30103", "账号不可用"),
    ACCOUNT_LOCKED("30104", "账号锁定"),
    ACCOUNT_NOT_EXIST("30105", "用户不存在"),
    ACCESS_DENIED("30106", "权限不足"),
    TOKEN_IS_NULL("30107", "token为空"),
    NOT_LOGIN("30107", "未登录"),
    ;

    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
