package com.hb.test.dubbo.server1.controller;

import com.hb.test.dubbo.server2.facade.Server2Facade;
import com.hb.test.dubbo.server3.facade.Server3Facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * test
 *
 * @version v0.1, 2020/12/9 22:17, create by huangbiao.
 */
@RestController
public class TestController {

    @Autowired
    private Server2Facade server2Facade;

    @Autowired
    private Server3Facade server3Facade;

    @RequestMapping("/test1")
    public Object test1() {
        return server2Facade.getServerName();
    }

    @RequestMapping("/test2")
    public Object test2() {
        return server3Facade.getServerName();
    }

}

    