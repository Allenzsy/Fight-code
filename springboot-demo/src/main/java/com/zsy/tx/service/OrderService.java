package com.zsy.tx.service;

import com.zsy.base.po.OrderDo;

import java.util.List;

/**
 * @Author zsy
 * @Date 2023/9/19 16:47
 * @Description: 订单服务接口
 */
public interface OrderService {

    /**
     * 创建多笔订单
     * @param orderDoList 订单列表
     * @return 成功创建订单数量
     */
    int createOrders(List<OrderDo> orderDoList);

    int createOrdersNotRecommend(List<OrderDo> orderDoList);

    int createOrdersNotRecommend2(List<OrderDo> orderDoList);

}
