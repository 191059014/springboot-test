package com.hb.test.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
//        http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class); // 验证码过滤器
        http
                .authorizeRequests()//开启登录配置
                .antMatchers("/v1").hasAuthority("p1")//表示访问/v1这个接口，需要具备r1这个角色
                .antMatchers("/v2").hasAuthority("p2")//表示访问/v2这个接口，需要具备r2这个角色
                .anyRequest().authenticated()//表示剩余的其他接口，登录之后就能访问
                .anyRequest().access("@rbacAuthorityService.hasPermission(request,authentication)")// rbac动态url认证
                .and()
                .formLogin()
                //定义登录页面，未登录时，访问一个需要登录之后才能访问的接口，会自动跳转到该页面
                .loginPage("/toLogin")
                //登录处理接口
                .loginProcessingUrl("/doLogin")
                //定义登录时，用户名的 key，默认为 username
                .usernameParameter("userName")
                //定义登录时，用户密码的 key，默认为 password
                .passwordParameter("password")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
                .permitAll()//和表单登录相关的接口统统都直接通过
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, resp, authentication) -> {
                    System.out.println("注销成功处理器");
                })
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .exceptionHandling().accessDeniedHandler((request, response, e) -> {
                    System.out.println("权限不足");
                    response.sendRedirect("/403");
                })
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略拦截
        web.ignoring().antMatchers("/static/**")
        .antMatchers("/403");
    }
}

    