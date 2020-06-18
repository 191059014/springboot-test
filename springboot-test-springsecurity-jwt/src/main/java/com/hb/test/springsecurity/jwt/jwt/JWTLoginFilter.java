package com.hb.test.springsecurity.jwt.jwt;

import com.hb.test.springsecurity.jwt.model.Permission;
import com.hb.test.springsecurity.jwt.model.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * jwt登陆过滤器
 *
 * @author Mr.Huang
 * @version v0.1, JWTLoginFilter.java, 2020/6/17 13:51, create by huangbiao.
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 接收并解析用户登陆信息  /login,
     * 为已验证的用户返回一个已填充的身份验证令牌，表示成功的身份验证
     * 返回null，表明身份验证过程仍在进行中。在返回之前，实现应该执行完成该过程所需的任何额外工作。
     * 如果身份验证过程失败，就抛出一个AuthenticationException
     *
     * @param request  从中提取参数并执行身份验证
     * @param response 如果实现必须作为多级身份验证过程的一部分(比如OpenID)进行重定向，则可能需要响应
     * @return 身份验证的用户令牌，如果身份验证不完整，则为null。
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("attemptAuthentication");
        //得到用户登陆信息,并封装到 Authentication 中,供自定义用户组件使用.
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ArrayList<Permission> authorities = new ArrayList<>();

        //authenticate()接受一个token参数,返回一个完全经过身份验证的对象，包括证书.
        // 这里并没有对用户名密码进行验证,而是使用 AuthenticationProvider 提供的 authenticate 方法返回一个完全经过身份验证的对象，包括证书.
        // Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // UsernamePasswordAuthenticationToken 是 Authentication 的实现类

        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }

    /**
     * 登陆成功后,此方法会被调用,因此我们可以在次方法中生成token,并返回给客户端
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = JwtUtils.generateJsonWebToken(((UserPrincipal) authResult.getPrincipal()).getUser());
        response.addHeader("token", "Bearer " + token);
    }
}

    