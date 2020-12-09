package com.hb.test.dubbo.server2.facade.impl;

import com.hb.test.dubbo.server2.facade.Server2Facade;

/**
 * 门面实现类
 *
 * @version v0.1, 2020/12/9 21:24, create by huangbiao.
 */
public class Server2FacadeImpl implements Server2Facade {

    @Override
    public String getServerName() {
        return "here is server2";
    }

}

    