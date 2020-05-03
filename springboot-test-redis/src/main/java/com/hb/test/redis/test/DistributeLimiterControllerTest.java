package com.hb.test.redis.test;

import com.hb.limiter.redis.RedisLimiter;
import com.hb.limiter.redis.annotation.RedisEasyLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ========== Description ==========
 *
 * @author Mr.huang
 * @version com.hb.test.redis.test.Test.java, v1.0
 * @date 2020年05月03日 22时29分
 */
@RestController
public class DistributeLimiterControllerTest {

    @Autowired
    RedisLimiter redisLimiter;

    @GetMapping("/test")
    @RedisEasyLimiter(key = "test", period = 10, maxTimes = 3)
    public void test() {
        System.out.println(111);
    }

    @GetMapping("/test1")
    public void test1() {
        if (redisLimiter.pass("",10,3)) {

        }
        System.out.println(111);
    }

}
