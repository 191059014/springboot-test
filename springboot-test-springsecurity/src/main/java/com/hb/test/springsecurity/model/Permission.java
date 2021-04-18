package com.hb.test.springsecurity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限
 *
 * @author Mr.Huang
 * @version v0.1, Permission.java, 2020/6/1 15:30, create by huangbiao.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    public Permission(String permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * 权限ID
     */
    private String permissionId;

    /**
     * 权限名
     */
    private String name;

    /**
     * 类型为页面时，代表前端路由地址，类型为按钮时，代表后端接口地址
     */
    private String url;

    /**
     * 权限类型，页面-1，按钮-2
     */
    private Integer type;

    /**
     * 权限表达式
     */
    private String permission;

    /**
     * 后端接口访问方式
     */
    private String method;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 父级id
     */
    private Long parentId;

}
