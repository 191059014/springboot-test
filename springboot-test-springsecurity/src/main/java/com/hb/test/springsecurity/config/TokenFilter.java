package com.hb.test.springsecurity.config;

import com.hb.test.springsecurity.model.Permission;
import com.hb.test.springsecurity.model.User;
import com.hb.test.springsecurity.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * token过滤器
 *
 * @version v0.1, 2021/4/18 1:33, create by huangbiao.
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String token = request.getHeader("token");
        if (!"".equals(token) && token != null) {
            String s = "zhangsan";
            User user = UserDetailServiceImpl.getUserByUserName(s);
            List<String> roleList = UserDetailServiceImpl.getRoleListByRoleIdArr(s);
            List<Permission> permissionList = UserDetailServiceImpl.getPermissionListByPermissionIdArr(s);
            UserPrincipal principal = new UserPrincipal(user, roleList, permissionList);
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

}
