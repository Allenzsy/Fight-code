package com.zsy.tx;

import com.zsy.base.config.DruidConfig;
import com.zsy.base.mapper.OrderMapper;
import com.zsy.base.po.OrderDo;
import com.zsy.tx.service.A;
import com.zsy.tx.service.AService;
import com.zsy.tx.service.SpringUtil;
import com.zsy.tx.service.TxSelfCallService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.Date;

/**
 * @Author Allenzsy
 * @Date 2022/8/17 22:47
 * @Description:
 */
@Slf4j
@MapperScan("com.zsy.base.mapper")
@SpringBootApplication(scanBasePackages = {"com.zsy.base", "com.zsy.tx"})
public class IOCTest_Tx {

    @Test
    public void transactional_self_call_invalid() {
        ConfigurableApplicationContext context = SpringApplication.run(IOCTest_Tx.class);
        OrderMapper orderMapper = context.getBean(OrderMapper.class);
        final OrderDo orderDo2 = orderMapper.queryUseDollar(1, "zsy' and '1=1");
        log.info("orderMapper.queryUseDollar: {}", orderDo2);
        final OrderDo orderDo3 = orderMapper.queryUseHashtag(1, "zsy' and '1=1");
        log.info("orderMapper.queryUseHashtag: {}", orderDo3);
        TxSelfCallService invalidService = context.getBean(TxSelfCallService.class);
        Object bean2 = context.getBean(DruidConfig.class);
        log.info("{}", invalidService);
        log.info("{}", bean2);
        OrderDo orderDo = new OrderDo(10, "测试1", new Date());
        OrderDo orderDo1 = new OrderDo(11, "测试2", new Date());
        ArrayList<OrderDo> list = new ArrayList<>();
        list.add(orderDo);
        list.add(orderDo1);
        log.info("直接调用 insertOrder 会正常创建一个事务");
        invalidService.insertOrder(orderDo);
        log.info("直接调用 insertListSelfCallInvalid 发生直接自调用 insertOrder 的事务会失效");
        invalidService.insertListSelfCallInvalid(list);
        log.info("直接调用 insertListSelfCallCorrect 从容器中获取代理对象调用 insertOrder 的事务不会失效");
        invalidService.insertListSelfCallCorrect(list);

        final A a = SpringUtil.getBean(A.class);
        System.out.println(a);
    }

    @Test
    public void test_A_Required_B_Required() {
        ConfigurableApplicationContext context = SpringApplication.run(IOCTest_Tx.class);
        AService aService = context.getBean(AService.class);
        aService.A_Required_B_Required();
    }

    @Test
    public void test_A_Required_B_RequiredNew() {
        ConfigurableApplicationContext context = SpringApplication.run(IOCTest_Tx.class);
        AService aService = context.getBean(AService.class);
        aService.A_Require_B_RequireNew();
    }

    @Test
    public void test_A_Required_B_NESTED() {
        ConfigurableApplicationContext context = SpringApplication.run(IOCTest_Tx.class);
        AService aService = context.getBean(AService.class);
        aService.A_Require_B_NESTED();
    }

    @Test
    public void test_A_Required_B_RequiredNew_Exception() {
        ConfigurableApplicationContext context = SpringApplication.run(IOCTest_Tx.class);
        AService aService = context.getBean(AService.class);
        try {
            aService.A_Require_B_RequiresNew_Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_A_Required_B_NESTED_Exception() {
        ConfigurableApplicationContext context = SpringApplication.run(IOCTest_Tx.class);
        AService aService = context.getBean(AService.class);
        try {
            aService.A_Require_B_NESTED_Exception();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Test
    //public void test_Tx() {
    //    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TxConfig.class);
    //    String[] names = context.getBeanDefinitionNames();
    //    for (String name : names) {
    //        log.info(name);
    //    }
    //}

}