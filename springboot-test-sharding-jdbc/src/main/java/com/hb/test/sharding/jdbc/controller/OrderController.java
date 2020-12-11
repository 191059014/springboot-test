package com.hb.test.sharding.jdbc.controller;

import com.hb.test.sharding.jdbc.common.DbAndTbEnum;
import com.hb.test.sharding.jdbc.dobj.OrderDO;
import com.hb.test.sharding.jdbc.service.IOrderService;
import com.hb.test.sharding.jdbc.util.ShardingUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

;

/**
 * 订单表(OrderDO)表控制层
 *
 * @author Mr.Huang
 * @since 2020-12-11 10:10:20
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    /**
     * 服务对象
     */
    @Resource
    private IOrderService iOrderService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectById")
    public OrderDO selectOne(Integer id) {
        return this.iOrderService.selectById(id);
    }

    /**
     * 添加
     *
     * @return 单条数据
     */
    @GetMapping("/addOne")
    public OrderDO addOne() {
        String mobile = "18310673016";
        OrderDO orderDO = new OrderDO();
        orderDO.setOrderId(ShardingUtils.getUniqKey(mobile, DbAndTbEnum.T_ORDER));
        orderDO.setMobile(mobile);
        this.iOrderService.insertBySelective(orderDO);
        return orderDO;
    }

}