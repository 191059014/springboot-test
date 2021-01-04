package com.hb.test.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.hb.test.nacos.config.NacosConfigProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试类
 *
 * @version v0.1, 2021/1/4 13:29, create by huangbiao.
 */
@RestController
public class TestController {

    @NacosValue(value = "${wfp_order_submit:null}", autoRefreshed = true)
    public String wfp_order_submit;

    @GetMapping("/test1")
    public void test1() {
        System.out.println("nacos配置：" + wfp_order_submit);
    }

    @GetMapping("/test2")
    public void test2() {
        System.out.println("nacos配置：" + NacosConfigProperties.getValue("wfp_order_submit"));
    }

}
