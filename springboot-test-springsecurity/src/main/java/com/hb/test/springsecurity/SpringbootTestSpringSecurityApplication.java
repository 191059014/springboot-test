package com.hb.test.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class SpringbootTestSpringSecurityApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootTestSpringSecurityApplication.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();
        String loginUrl = String.format("http://localhost:%s/login.html", environment.getProperty("server.port"));
        System.out.println("==============================================================");
        System.out.println("    enjoy you self，more please see: " + loginUrl);
        System.out.println("==============================================================");
    }

}
