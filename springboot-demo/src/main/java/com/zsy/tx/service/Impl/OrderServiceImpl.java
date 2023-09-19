package com.zsy.tx.service.Impl;

import com.zsy.base.mapper.OrderMapper;
import com.zsy.base.po.OrderDo;
import com.zsy.tx.service.OrderService;
import com.zsy.tx.service.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author zsy
 * @Date 2023/9/19 17:04
 * @Description: 订单服务接口实现类
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    /**
     * 创建多笔订单
     * @param orderDoList 订单列表
     * @return 成功创建订单数量
     */
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    @Override
    public int createOrders(List<OrderDo> orderDoList) {
        int count = 0;
        for(OrderDo order : orderDoList) {
            orderMapper.insertOrder(order);
            count++;
        }
        return count;
    }

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = Exception.class)
    @Override
    public int createOrdersNotRecommend(List<OrderDo> orderDoList) {
        int count = 0;
        SqlSessionTemplate sessionTemplate = SpringUtil.getBean(SqlSessionTemplate.class);
        for(OrderDo order : orderDoList) {
            sessionTemplate.insert("com.zsy.base.mapper.OrderMapper.insertOrder", order);
            try {
                sessionTemplate.commit();
            } catch (Exception e) {
                log.error("企手项目里吞掉的异常 {}", e);
            }
            count++;
        }
        return count;
    }

    @Override
    public int createOrdersNotRecommend2(List<OrderDo> orderDoList) {
        int count = 0;
        SqlSessionTemplate sessionTemplate = SpringUtil.getBean(SqlSessionTemplate.class);
        for(OrderDo order : orderDoList) {
            sessionTemplate.insert("com.zsy.base.mapper.OrderMapper.insertOrder", order);
            try {
                sessionTemplate.commit();
            } catch (Exception e) {
                log.error("企手项目里吞掉的异常 {}", e);
            }
            count++;
        }
        return count;
    }
}
