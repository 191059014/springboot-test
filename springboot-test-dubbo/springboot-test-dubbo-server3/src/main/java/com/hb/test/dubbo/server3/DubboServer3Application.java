package com.hb.test.dubbo.server3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * DubboServer2Application
 */
@SpringBootApplication
@ImportResource({"classpath:META-INF/server3/applicationContext-dubbo.xml"})
public class DubboServer3Application {

    public static void main(String[] args) {
        SpringApplication.run(DubboServer3Application.class, args);
    }

}
