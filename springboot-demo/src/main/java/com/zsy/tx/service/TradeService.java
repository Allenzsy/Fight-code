package com.zsy.tx.service;

import com.zsy.base.po.OrderDo;

import java.util.List;

/**
 * @Author zsy
 * @Date 2023/9/19 16:40
 * @Description: 模拟业务入口服务
 */
public interface TradeService {

    /**
     * 秒杀订单
     * @param orderDoList 订单列表
     * @return 成功秒杀订单数量
     */
    int secKill(List<OrderDo> orderDoList);

    int secKillNotRecommend(List<OrderDo> orderDoList);

    int secKillNotRecommend2(List<OrderDo> orderDoList);

}
