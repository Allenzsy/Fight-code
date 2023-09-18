package com.zsy.tx.service;

import com.zsy.base.mapper.OrderMapper;
import com.zsy.base.po.OrderDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class AService {

    @Autowired
    BService bService;

    @Autowired
    OrderMapper orderMapper;

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED)
    public void A_Required_B_Required() {
        OrderDo A_orderDo = new OrderDo(311, "A_Required", new Date());
        orderMapper.insertOrder(A_orderDo);
        bService.B_Required();
        List<OrderDo> orderDos = orderMapper.queryList(IntStream.of(311, 312).boxed().collect(Collectors.toList()));
        log.info("A_Required 的查询结果: {}", orderDos);
        log.info("因为A和B都是 REQUIRED 时, 因此B会加入到A的事务中, 所以B可以查看到A插入的数据");
    }

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED)
    public void A_Require_B_RequireNew() {
        OrderDo A_orderDo = new OrderDo(321, "A_Required", new Date());
        orderMapper.insertOrder(A_orderDo);
        bService.B_RequiredNew();
        List<OrderDo> orderDos = orderMapper.queryList(IntStream.of(321, 322).boxed().collect(Collectors.toList()));
        log.info("A_Required 的查询结果: {}", orderDos);
        log.info("因为B是 REQUIRES_NEW, 因此B会挂起A的事务然后新创建一个事务, 等B运行完才会继续A, 所以B查不到A插入的数据");
    }

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED)
    public void A_Require_B_NESTED() {
        OrderDo A_orderDo = new OrderDo(331, "A_Required", new Date());
        orderMapper.insertOrder(A_orderDo);
        bService.B_NESTED();
        List<OrderDo> orderDos = orderMapper.queryList(IntStream.of(331, 332).boxed().collect(Collectors.toList()));
        log.info("A_Required 的查询结果: {}", orderDos);
        log.info("因为B是 NESTED, 因此若数据库支持savepoint则B会作为子事务加入A的事务, 所以A、B互相可以看到数据");
    }

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED)
    public void A_Require_B_RequiresNew_Exception() {
        OrderDo A_orderDo = new OrderDo(341, "A_Required", new Date());
        orderMapper.insertOrder(A_orderDo);
        bService.B_RequiredNew();
        log.info("因为B是 REQUIRES_NEW, 因此B会挂起A的事务然后新创建一个事务, A发生异常B不会回滚");
        int e = 3/0;
    }

    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED)
    public void A_Require_B_NESTED_Exception() {
        OrderDo A_orderDo = new OrderDo(351, "A_Required", new Date());
        orderMapper.insertOrder(A_orderDo);
        bService.B_NESTED();
        log.info("因为B是 NESTED, 因此若数据库支持 savepoint B会作为子事务加入A事务, A发生异常B会回滚");
        int e = 3/0;
    }
}
