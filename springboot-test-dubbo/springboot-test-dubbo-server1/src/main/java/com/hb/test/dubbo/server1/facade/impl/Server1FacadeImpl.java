package com.hb.test.dubbo.server1.facade.impl;

import com.hb.test.dubbo.server1.facade.Server1Facade;

/**
 * 门面实现类
 *
 * @version v0.1, 2020/12/9 21:24, create by huangbiao.
 */
public class Server1FacadeImpl implements Server1Facade {

    @Override
    public String getServerName() {
        return "here is server1";
    }

}

    