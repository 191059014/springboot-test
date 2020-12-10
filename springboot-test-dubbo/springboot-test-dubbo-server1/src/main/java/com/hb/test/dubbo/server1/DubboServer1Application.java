package com.hb.test.dubbo.server1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * DubboServer1Application
 */
@SpringBootApplication
@ImportResource({"classpath:META-INF/server1/applicationContext-dubbo.xml"})
public class DubboServer1Application {

    public static void main(String[] args) {
        SpringApplication.run(DubboServer1Application.class, args);
    }

}
