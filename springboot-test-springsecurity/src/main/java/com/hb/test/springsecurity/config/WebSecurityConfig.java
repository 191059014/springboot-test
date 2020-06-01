package com.hb.test.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
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

        http.authorizeRequests() // 拦截页面
                .anyRequest()
                .authenticated(); // 所有页面都要验证

        http.csrf().disable(); // 禁用csrf - 使用自定义登录页面

        http.formLogin() // 登陆
                .loginPage("/login") // 默认登陆页面
                .usernameParameter("userName") // 定义登录时，用户名的 key，默认为 userName
                .passwordParameter("password") // 定义登录时，用户名的 key，默认为 password
                .defaultSuccessUrl("/home", true)
                .failureForwardUrl("/home")
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> System.out.println("认证成功"))
                .failureHandler((httpServletRequest, httpServletResponse, e) -> System.out.println("认证失败"))
                .withObjectPostProcessor(new ObjectPostProcessor<Object>() {
                    @Override
                    public <O> O postProcess(O o) {
                        System.out.println("withObjectPostProcessor");
                        return o;
                    }
                })
                .permitAll();

        http.userDetailsService(userDetailsService);


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 跳过一些请求，不用认证
        web.ignoring()
                .antMatchers("/ignore")
                .antMatchers("/doLogin");
    }
}

    