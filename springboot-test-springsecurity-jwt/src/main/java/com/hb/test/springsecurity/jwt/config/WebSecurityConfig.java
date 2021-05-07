package com.hb.test.springsecurity.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

/**
 * SpringSecurity配置
 *
 * @author Mr.Huang
 * @version v0.1, 2020/6/1 14:40, create by huangbiao.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Autowired
    private CustomLoginFailureHandler customLoginFailureHandler;

    @Autowired
    private CustomLogoutHandler customLogoutHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private TokenFilter tokenFilter;

    @Value("${security.loginUrl}")
    private String loginUrl;

    @Value("${security.logoutUrl}")
    private String logoutUrl;

    @Value("${security.ignoreUrls}")
    private String ignoreUrls;

    /**
     * 密码加密器
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 密码加密
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
            .cors().and().csrf().disable()
            // 基于token，所以不需要session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            // 打开认证配置
            .and().authorizeRequests()
            // 其他所有请求需要身份认证
            .anyRequest().authenticated();

        http
            // 指定表单登陆方式
            .formLogin()
            // 指定登录处理的url（当请求为此路径时才被认为是登陆）
            .loginProcessingUrl(loginUrl)
            // 定义登录时的用户名字段
            .usernameParameter("username")
            // 定义登录时的密码字段
            .passwordParameter("password")
            // 登陆成功处理器
            .successHandler(customLoginSuccessHandler)
            // 登陆失败处理器
            .failureHandler(customLoginFailureHandler)
            // 和登录相关的接口直接跳过权限
            .permitAll();

        http
            // 开启注销设置
            .logout()
            // 指定注销处理url（当请求为此路径时才被认为是注销）
            .logoutUrl(logoutUrl)
            // 注销处理器
            .addLogoutHandler(customLogoutHandler)
            // 注销成功处理器
            .logoutSuccessHandler(customLogoutSuccessHandler)
            // 和登录相关的接口直接跳过权限
            .permitAll();

        http
            // 开启异常处理
            .exceptionHandling()
            // 认证过的用户访问无权限资源时的异常处理器
            .accessDeniedHandler(customAccessDeniedHandler)
            // 匿名用户访问无权限资源时的异常处理器
            .authenticationEntryPoint(customAuthenticationEntryPoint);

        // 验证码过滤器
        // http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);

        // 因为是前后端分离的项目，所以要加一个token过滤器
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略请求
        WebSecurity ws = web.ignoring().and();
        String[] ignoreUrlArr = ignoreUrls.split(",");
        System.out.println("忽略的请求路径：" + Arrays.toString(ignoreUrlArr));
        for (String ignoreUrl : ignoreUrlArr) {
            ws.ignoring().antMatchers(ignoreUrl);
        }

    }
}
