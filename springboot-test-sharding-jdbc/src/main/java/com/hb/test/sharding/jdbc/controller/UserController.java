package com.hb.test.sharding.jdbc.controller;


import com.hb.test.sharding.jdbc.dobj.UserDO;
import com.hb.test.sharding.jdbc.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户表(UserDO)表控制层
 *
 * @author Mr.Huang
 * @since 2020-12-11 10:28:50
 */
@RestController
@RequestMapping("tUser0000")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private IUserService iUserService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectById")
    public UserDO selectOne(Integer id) {
        return this.iUserService.selectById(id);
    }

}