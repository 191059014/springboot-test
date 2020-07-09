package com.hb.test.log.support.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试日志
 *
 * @version v0.1, 2020/7/9 14:23, create by huangbiao.
 */
@RestController
public class LogController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogController.class);

    @GetMapping("/print")
    public void print() {
        LOGGER.info("这里是日志");
    }

    @Scheduled(fixedDelay = 5000)
    public void printScheduled() {
        LOGGER.info("这里是定时任务日志");
    }

}

    