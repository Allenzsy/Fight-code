package com.zsy.tx.service;

import com.zsy.base.mapper.OrderMapper;
import com.zsy.base.po.OrderDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Allenzsy
 * @Date 2022/8/9 22:43
 * @Description:
 */
@Service
public class TxSelfCallServiceImpl implements TxSelfCallService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
                   isolation = Isolation.READ_COMMITTED)
    public int insertOrder(OrderDo orderDo) {
        int count = orderMapper.insertOrder(orderDo);
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
                   isolation = Isolation.READ_COMMITTED)
    public int insertListSelfCallInvalid(List<OrderDo> orderDoList) {
        int count = 0;
        for(OrderDo order : orderDoList) {
            insertOrder(order);
            count++;
        }
        return 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED)
    public int insertListSelfCallCorrect(List<OrderDo> orderDoList) {
        int count = 0;
        TxSelfCallService service = SpringUtil.getBean(TxSelfCallService.class);
        for(OrderDo order : orderDoList) {
            service.insertOrder(order);
            count++;
        }
        return 0;
    }
}
