package com.hb.test.sharding.jdbc.controller;

import com.hb.test.sharding.jdbc.common.DbAndTbEnum;
import com.hb.test.sharding.jdbc.dobj.UserDO;
import com.hb.test.sharding.jdbc.service.IUserService;
import com.hb.test.sharding.jdbc.util.KeyGenerator;
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
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private IUserService iUserService;

    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("/selectList")
    public Object selectList() {
        UserDO userDO = new UserDO();
        userDO.setId(6);
        return this.iUserService.selectList(userDO);
    }

    /**
     * 添加
     *
     * @return 数据
     */
    @GetMapping("/addBatch")
    public Object addBatch() {
        for (int i = 0; i < 10; i++) {
            String mobile = "1660710728" + i;
            UserDO userDO = new UserDO();
            userDO.setUserId(KeyGenerator.getUniqKey(mobile, DbAndTbEnum.T_USER));
            userDO.setMobile(mobile);
            this.iUserService.insertBySelective(userDO);
        }

        return "success";
    }

}