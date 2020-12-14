package com.hb.test.sharding.jdbc.service.impl;


import com.hb.test.sharding.jdbc.dao.IOrderDao;
import com.hb.test.sharding.jdbc.dobj.OrderDO;
import com.hb.test.sharding.jdbc.service.IOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 订单表(OrderDO)表服务实现类
 *
 * @author Mr.Huang
 * @since 2020-12-11 10:10:20
 */
@Service
public class OrderServiceImpl implements IOrderService {

    /**
     * 数据库dao操作类
     */
    @Resource
    private IOrderDao iOrderDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OrderDO selectById(Integer id) {
        return this.iOrderDao.selectById(id);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param orderDO 实例对象
     * @return 对象列表
     */
    @Override
    public List<OrderDO> selectList(OrderDO orderDO) {
        return this.iOrderDao.selectList(orderDO);
    }

    /**
     * 查询指定行数据
     *
     * @param orderDO 实例对象
     * @return 对象列表
     */
    @Override
    public Integer selectCount(OrderDO orderDO) {
        return this.iOrderDao.selectCount(orderDO);
    }

    /**
     * 查询指定行数据
     *
     * @param orderDO 实例对象
     * @param offset  查询起始位置
     * @param limit   查询条数
     * @return 对象列表
     */
    @Override
    public List<OrderDO> selectPages(OrderDO orderDO, int offset, int limit) {
        return this.iOrderDao.selectPages(orderDO, offset, limit);
    }

    /**
     * 选择性新增
     *
     * @param orderDO 实例对象
     * @return 影响行数
     */
    @Override
    public int insertBySelective(OrderDO orderDO) {
        return this.iOrderDao.insertBySelective(orderDO);
    }

    /**
     * 选择性修改
     *
     * @param orderDO 实例对象
     * @return 影响行数
     */
    @Override
    public int updateBySelective(OrderDO orderDO) {
        return this.iOrderDao.updateBySelective(orderDO);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Override
    public int deleteById(Integer id) {
        return this.iOrderDao.deleteById(id);
    }

    @Override
    public List<OrderDO> selectByOrders(Set<String> set) {
        return this.iOrderDao.selectByOrders(set);
    }

}