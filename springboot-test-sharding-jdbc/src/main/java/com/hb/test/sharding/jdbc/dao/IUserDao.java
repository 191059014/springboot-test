package com.hb.test.sharding.jdbc.dao;

import com.hb.test.sharding.jdbc.dobj.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表(UserDO)表数据库访问层
 *
 * @author Mr.Huang
 * @since 2020-12-11 10:28:50
 */
@Mapper
public interface IUserDao {

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
     * 条件查询总条数
     *
     * @param userDO 实例对象
     * @return 对象列表
     */
    Integer selectCount(@Param("userDO") UserDO userDO);

    /**
     * 查询指定行数据
     *
     * @param userDO 实例对象
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<UserDO> selectPages(@Param("userDO") UserDO userDO, @Param("offset") int offset, @Param("limit") int limit);

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