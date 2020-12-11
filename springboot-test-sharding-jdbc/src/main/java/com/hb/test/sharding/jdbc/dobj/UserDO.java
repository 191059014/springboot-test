package com.hb.test.sharding.jdbc.dobj;

import java.io.Serializable;

/**
 * 用户表(UserDO)实体类
 *
 * @author Mr.Huang
 * @since 2020-12-11 10:28:50
 */
public class UserDO implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -88216866726003641L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户标识
     */
    private String userId;
    /**
     * 手机号
     */
    private String mobile;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}