package com.hb.test.email;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class SpringbootTestEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTestEmailApplication.class, args);
    }

}
