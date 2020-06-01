package com.hb.test.springsecurity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * 权限
 *
 * @author Mr.Huang
 * @version v0.1, Permission.java, 2020/6/1 15:30, create by huangbiao.
 */
@Data
@AllArgsConstructor
public class Permission implements GrantedAuthority {

    /**
     * 权限ID
     */
    private String permissionId;

    @Override
    public String getAuthority() {
        return permissionId;
    }
}

    