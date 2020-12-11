package com.hb.test.sharding.jdbc.dobj;

import java.io.Serializable;

/**
 * 订单表(OrderDO)实体类
 *
 * @author Mr.Huang
 * @since 2020-12-11 10:10:20
 */
public class OrderDO implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 130177308633945054L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 订单标识
     */
    private String orderId;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}