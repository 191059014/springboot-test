package com.hb.test.springsecurity.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用户模型
 *
 * @author Mr.Huang
 * @version v0.1, User.java, 2020/6/1 15:28, create by huangbiao.
 */
@Data
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = -4402301872408023770L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色
     */
    private List<Role> roles;

    /**
     * 权限
     */
    private List<Permission> permissions;

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection authorities = new ArrayList();
        if (!CollectionUtils.isEmpty(permissions)) {
            permissions.forEach(permission -> {
                authorities.add(new SimpleGrantedAuthority(permission.getPermissionId()));
            });
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
