package com.hb.test.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * SpringSecurity配置
 *
 * @author Mr.Huang
 * @version v0.1, WebSecurityConfig.java, 2020/6/1 14:40, create by huangbiao.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

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
//super.configure(http);
//        http.authorizeRequests() // 拦截页面
//                .anyRequest()
//                .authenticated(); // 所有页面都要验证
//
        http.csrf().disable(); // 禁用csrf - 使用自定义登录页面
//
//        http.formLogin() // 登陆
//                .loginPage("/login") // 访问需要登录才能访问的页面，如果未登录，会跳转到该地址来
//                .successHandler(new MyAuthenticationSuccessHandler())
//                .failureHandler(new MyAuthenticationFailureHandler())
//        ;

        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/images/**").permitAll()
                .antMatchers("/static/login.html", "/ignore").permitAll() // 所有用户均可访问的资源路径
                .antMatchers("/sys").hasRole("admin")
                .antMatchers("/menu").hasAuthority("p1")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/static/login.html") // 访问需要登录才能访问的页面，如果未登录，会跳转到该地址来
                .loginProcessingUrl("/doLogin")
                .usernameParameter("userName")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .failureUrl("/ignore")
                .successHandler(new MyAuthenticationSuccessHandler())
                .failureHandler(new MyAuthenticationFailureHandler())

        ;


    }

}

    