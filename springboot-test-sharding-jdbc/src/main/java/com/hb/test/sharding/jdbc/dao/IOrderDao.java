package com.hb.test.sharding.jdbc.dao;

import com.hb.test.sharding.jdbc.dobj.OrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 订单表(OrderDO)表数据库访问层
 *
 * @author Mr.Huang
 * @since 2020-12-11 10:10:20
 */
@Mapper
public interface IOrderDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    OrderDO selectById(Integer id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param orderDO 实例对象
     * @return 对象列表
     */
    List<OrderDO> selectList(OrderDO orderDO);

    /**
     * 条件查询总条数
     *
     * @param orderDO 实例对象
     * @return 对象列表
     */
    Integer selectCount(@Param("orderDO") OrderDO orderDO);

    /**
     * 查询指定行数据
     *
     * @param orderDO 实例对象
     * @param offset  查询起始位置
     * @param limit   查询条数
     * @return 对象列表
     */
    List<OrderDO> selectPages(@Param("orderDO") OrderDO orderDO, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 选择性新增
     *
     * @param orderDO 实例对象
     * @return 影响行数
     */
    int insertBySelective(OrderDO orderDO);

    /**
     * 选择性修改
     *
     * @param orderDO 实例对象
     * @return 影响行数
     */
    int updateBySelective(OrderDO orderDO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 通过order_id集合查询
     *
     * @param set
     *            id集合
     * @return 集合
     */
    List<OrderDO> selectByOrders(@Param("set") Set<String> set);
}