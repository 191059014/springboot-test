package com.hb.test.dubbo.server3.facade.impl;

import com.hb.test.dubbo.server3.facade.Server3Facade;

/**
 * 门面实现类
 *
 * @version v0.1, 2020/12/9 21:24, create by huangbiao.
 */
public class Server3FacadeImpl implements Server3Facade {

    @Override
    public String getServerName() {
        return "here is server3";
    }

}

    