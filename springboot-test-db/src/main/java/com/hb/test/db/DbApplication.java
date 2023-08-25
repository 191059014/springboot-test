package com.hb.test.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数据库
 */
@SpringBootApplication
public class DbApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbApplication.class, args);
        Set<Long> ids = new HashSet<>();
        List<Long> list = null;
        if (CollectionUtils.isEmpty()) {
            return CollectionUtils.isEmpty(list)? null : list.get(0);
        }
    }

}
