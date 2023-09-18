package com.zsy.tx.service;

import com.zsy.base.mapper.OrderMapper;
import com.zsy.base.po.OrderDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author Allenzsy
 * @Date 2022/8/18 0:04
 * @Description:
 */
@Slf4j
@Service
public class BService {

    @Resource
    OrderMapper orderMapper;

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED)
    public void B_Required() {
        OrderDo B_orderDo = new OrderDo(312, "B_Required", new Date());
        orderMapper.insertOrder(B_orderDo);
        List<OrderDo> orderDos = orderMapper.queryList(IntStream.of(311, 312).boxed().collect(Collectors.toList()));
        log.info("B_Required 的查询结果: {}", orderDos);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.READ_COMMITTED)
    public void B_RequiredNew() {
        OrderDo B_orderDo = new OrderDo(322, "B_RequiresNew", new Date());
        orderMapper.insertOrder(B_orderDo);
        List<OrderDo> orderDos = orderMapper.queryList(IntStream.of(321, 322).boxed().collect(Collectors.toList()));
        log.info("B_RequiresNew 的查询结果: {}", orderDos);
    }

    @Transactional(propagation = Propagation.NESTED,
            isolation = Isolation.READ_COMMITTED)
    public void B_NESTED() {
        OrderDo B_orderDo = new OrderDo(332, "B_NESTED", new Date());
        orderMapper.insertOrder(B_orderDo);
        List<OrderDo> orderDos = orderMapper.queryList(IntStream.of(331, 332).boxed().collect(Collectors.toList()));
        log.info("B_NESTED 的查询结果: {}", orderDos);
    }

}
