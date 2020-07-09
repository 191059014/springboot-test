package com.hb.test.log.support;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootTestLogSupportApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTestLogSupportApplication.class, args);
    }

}
