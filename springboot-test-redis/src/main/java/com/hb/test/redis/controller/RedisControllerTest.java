package com.hb.test.redis.controller;

import com.hb.test.redis.container.SpringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 测试
 *
 * @author Mr.Huang
 * @version v0.1, RedisControllerTest.java, 2020/6/18 9:20, create by huangbiao.
 */
@RestController
public class RedisControllerTest {

    @Resource(name = "integerRedisTemplate")
    private RedisTemplate<String, Integer> integerRedisTemplate;

    @Resource(name = "mapRedisTemplate")
    private RedisTemplate<String, Map<String, Object>> mapRedisTemplate;

    @Resource(name = "listRedisTemplate")
    private RedisTemplate<String, List<Object>> listRedisTemplate;

    @Resource(name = "stringRedisTemplate")
    private RedisTemplate<String, String> stringRedisTemplate;

    @GetMapping("/testPrintAllBeans")
    public void testPrintAllBeans() {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);
    }

    @GetMapping("/testPrintRedisTemplate")
    public void testPrintRedisTemplate() {
        System.out.println(integerRedisTemplate);
        System.out.println(mapRedisTemplate);
        System.out.println(listRedisTemplate);
        System.out.println(stringRedisTemplate);
    }

}

    