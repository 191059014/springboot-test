package com.hb.test.sharding.jdbc.controller;

import com.hb.test.sharding.jdbc.common.DbAndTbEnum;
import com.hb.test.sharding.jdbc.dobj.OrderDO;
import com.hb.test.sharding.jdbc.service.IOrderService;
import com.hb.test.sharding.jdbc.util.KeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

;

/**
 * 订单表(OrderDO)表控制层
 *
 * @author Mr.Huang
 * @since 2020-12-11 10:10:20
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    /**
     * 服务对象
     */
    @Resource
    private IOrderService iOrderService;

    /**
     * 通过主键查询单条数据
     *
     * @param id
     *            主键
     * @return 单条数据
     */
    @GetMapping("/selectById")
    public OrderDO selectOne(Integer id) {
        return this.iOrderService.selectById(1);
    }

    /**
     * 查询集合
     *
     * @return 集合
     */
    @GetMapping("/selectByOrders")
    public Object selectByOrders() {
        Set<String> set = new HashSet<>();
        set.add("OD0200011607911567291");
        set.add("OD0100001607911567269");
        return this.iOrderService.selectByOrders(set);
    }

    /**
     * 查询集合
     *
     * @param id
     *            主键
     * @return 集合
     */
    @GetMapping("/selectList")
    public Object selectList(Integer id) {
        OrderDO query = new OrderDO();
//        query.setId(1); // 查询条件不是配置的分片键，则不进行分库分表路由，而是所有库表扫描
        // query.setOrderId("OD0200011607911567291"); // 查询条件是配置的分片键，则进行分库分表路由
        query.setMobile("16607107282");
        return this.iOrderService.selectList(query);
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
            OrderDO orderDO = new OrderDO();
            orderDO.setOrderId(KeyGenerator.getUniqKey(mobile, DbAndTbEnum.T_ORDER));
            orderDO.setMobile(mobile);
            this.iOrderService.insertBySelective(orderDO);
        }

        return "success";
    }

}