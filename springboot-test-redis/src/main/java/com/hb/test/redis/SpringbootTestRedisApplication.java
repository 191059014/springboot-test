package com.hb.test.redis;

import com.hb.limiter.redis.annotation.EnableRedisLimiter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRedisLimiter
@SpringBootApplication
public class SpringbootTestRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTestRedisApplication.class, args);
    }

}
