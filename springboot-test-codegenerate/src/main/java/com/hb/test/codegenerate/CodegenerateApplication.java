package com.hb.test.codegenerate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@SpringBootApplication
public class CodegenerateApplication {

    public static void main(String[] args) {

        SpringApplication.run(CodegenerateApplication.class, args);

        System.out.println("==================================================");
        System.out.println("      http://localhost:9999/index.html");
        System.out.println("==================================================");
    }

}
