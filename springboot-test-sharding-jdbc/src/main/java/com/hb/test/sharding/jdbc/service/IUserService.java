package com.hb.test.sharding.jdbc.service;

import com.hb.test.sharding.jdbc.dobj.UserDO;

import java.util.List;

/**
 * 用户表(UserDO)表服务接口
 *
 * @author Mr.Huang
 * @since 2020-12-11 10:28:50
 */
public interface IUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserDO selectById(Integer id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param userDO 实例对象
     * @return 对象列表
     */
    List<UserDO> selectList(UserDO userDO);

    /**
     * 查询指定行数据
     *
     * @param userDO 实例对象
     * @return 对象列表
     */
    Integer selectCount(UserDO userDO);

    /**
     * 查询指定行数据
     *
     * @param userDO 实例对象
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<UserDO> selectPages(UserDO userDO, int offset, int limit);

    /**
     * 选择性新增
     *
     * @param userDO 实例对象
     * @return 影响行数
     */
    int insertBySelective(UserDO userDO);

    /**
     * 选择性修改
     *
     * @param userDO 实例对象
     * @return 影响行数
     */
    int updateBySelective(UserDO userDO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}