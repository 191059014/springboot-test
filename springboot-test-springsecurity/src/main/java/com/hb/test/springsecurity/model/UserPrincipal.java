package com.hb.test.springsecurity.model;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 用户信息
 *
 * @author Mr.Huang
 * @version v0.1, UserPrincipal.java, 2020/6/1 15:25, create by huangbiao.
 */
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    /**
     * 用户信息
     */
    private User user;

    /**
     * 角色集合
     */
    private List<String> roleList;

    /**
     * 权限集合
     */
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
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

    public User getUser() {
        return user;
    }
}
