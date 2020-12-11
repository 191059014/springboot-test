package com.hb.test.sharding.jdbc.service.impl;

import com.hb.test.sharding.jdbc.dao.IUserDao;
import com.hb.test.sharding.jdbc.dobj.UserDO;
import com.hb.test.sharding.jdbc.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户表(UserDO)表服务实现类
 *
 * @author Mr.Huang
 * @since 2020-12-11 10:28:50
 */
@Service
public class UserServiceImpl implements IUserService {

    /**
     * 数据库dao操作类
     */
    @Resource
    private IUserDao iUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserDO selectById(Integer id) {
        return this.iUserDao.selectById(id);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param userDO 实例对象
     * @return 对象列表
     */
    @Override
    public List<UserDO> selectList(UserDO userDO) {
        return this.iUserDao.selectList(userDO);
    }

    /**
     * 查询指定行数据
     *
     * @param userDO 实例对象
     * @return 对象列表
     */
    @Override
    public Integer selectCount(UserDO userDO) {
        return this.iUserDao.selectCount(userDO);
    }

    /**
     * 查询指定行数据
     *
     * @param userDO 实例对象
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<UserDO> selectPages(UserDO userDO, int offset, int limit) {
        return this.iUserDao.selectPages(userDO, offset, limit);
    }

    /**
     * 选择性新增
     *
     * @param userDO 实例对象
     * @return 影响行数
     */
    @Override
    public int insertBySelective(UserDO userDO) {
        return this.iUserDao.insertBySelective(userDO);
    }

    /**
     * 选择性修改
     *
     * @param userDO 实例对象
     * @return 影响行数
     */
    @Override
    public int updateBySelective(UserDO userDO) {
        return this.iUserDao.updateBySelective(userDO);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Override
    public int deleteById(Integer id) {
        return this.iUserDao.deleteById(id);
    }

}