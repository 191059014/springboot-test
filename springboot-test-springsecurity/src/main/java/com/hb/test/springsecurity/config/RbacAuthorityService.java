package com.hb.test.springsecurity.config;

import com.hb.test.springsecurity.common.Consts;
import com.hb.test.springsecurity.model.Permission;
import com.hb.test.springsecurity.model.Role;
import com.hb.test.springsecurity.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * url动态权限验证
 */
@Component
public class RbacAuthorityService {

    @Autowired
    private RequestMappingHandlerMapping mapping;

    /**
     * 校验是否含有权限
     *
     * @param request        请求
     * @param authentication 认真信息
     * @return true为有权限
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object userInfo = authentication.getPrincipal();
        boolean hasPermission = false;

        if (userInfo instanceof UserDetails) {
            UserPrincipal principal = (UserPrincipal) userInfo;
            Long userId = principal.getUser().getUserId();

            List<Role> roles = this.selectByUserId(userId);
            List<String> roleIds = roles.stream().map(Role::getRoleId).collect(Collectors.toList());
            List<Permission> permissions = this.selectByRoleIdList(roleIds);

            //获取资源，前后端分离，所以过滤页面权限，只保留按钮权限
            List<Permission> btnPerms = permissions.stream()
                    // 过滤页面权限
                    .filter(permission -> Objects.equals(permission.getType(), Consts.BUTTON))
                    // 过滤 URL 为空
                    .filter(permission -> !StringUtils.isEmpty(permission.getUrl()))
                    // 过滤 METHOD 为空
                    .filter(permission -> !StringUtils.isEmpty(permission.getMethod()))
                    .collect(Collectors.toList());

            for (Permission btnPerm : btnPerms) {
                AntPathRequestMatcher antPathMatcher = new AntPathRequestMatcher(btnPerm.getUrl(), btnPerm.getMethod());// 也可以只通过url来匹配
                if (antPathMatcher.matches(request)) {
                    hasPermission = true;
                    break;
                }
            }

            return hasPermission;
        } else {
            return false;
        }
    }

    private List<Permission> selectByRoleIdList(List<String> roleIds) {
        List<Permission> permissions = new ArrayList<>();
        Permission permission = new Permission();
        permission.setUrl("/v1");
        permission.setMethod("GET");
        permission.setType(Consts.BUTTON);
        permissions.add(permission);
        return permissions;
    }

    private List<Role> selectByUserId(Long userId) {
        return Arrays.asList(new Role("r1"));
    }

}