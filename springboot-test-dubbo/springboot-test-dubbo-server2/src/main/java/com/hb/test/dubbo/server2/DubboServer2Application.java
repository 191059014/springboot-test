package com.hb.test.dubbo.server2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * DubboServer2Application
 */
@SpringBootApplication
@ImportResource({"classpath:META-INF/server2/applicationContext-*.xml"})
public class DubboServer2Application {

    public static void main(String[] args) {
        SpringApplication.run(DubboServer2Application.class, args);
    }

}
