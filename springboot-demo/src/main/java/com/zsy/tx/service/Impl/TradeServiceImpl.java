package com.zsy.tx.service.Impl;

import com.zsy.base.po.OrderDo;
import com.zsy.tx.service.OrderService;
import com.zsy.tx.service.TradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zsy
 * @Date 2023/9/19 16:46
 * @Description: 模拟业务入口服务实现类
 */
@Service
public class TradeServiceImpl implements TradeService {

    @Resource
    OrderService orderService;

    /**
     * 秒杀订单
     * @param orderDoList 订单列表
     * @return 成功秒杀订单数量
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    @Override
    public int secKill(List<OrderDo> orderDoList) {
        // 假设通过订单校验，已秒杀成功
        return orderService.createOrders(orderDoList);
    }

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    @Override
    public int secKillNotRecommend(List<OrderDo> orderDoList) {
        return orderService.createOrdersNotRecommend(orderDoList);
    }

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    @Override
    public int secKillNotRecommend2(List<OrderDo> orderDoList) {
        return orderService.createOrdersNotRecommend2(orderDoList);
    }
}
