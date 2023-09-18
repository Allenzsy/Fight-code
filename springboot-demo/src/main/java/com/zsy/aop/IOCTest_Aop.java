package com.zsy.aop;

import com.zsy.aop.service.AService;
import com.zsy.aop.service.AServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author Allenzsy
 * @Date 2022/8/17 22:47
 * @Description:
 */
@Slf4j
//@EnableAspectJAutoProxy 默认是开启的
@SpringBootApplication(scanBasePackages = {"com.zsy.aop"})
public class IOCTest_Aop {
    /**
     * 使用 AOP 且生效需要注意以下几点:
     * 1. 切面类也需要作为组件放入 IoC 容器中, @EnableAspectJAutoProxy 不是必须的, 当引入 AOP 相关依赖之后默认开启
     * 2. 切面类中定义切点和通知方法, 注意: 当 AfterReturning 和 Around共存时，AfterReturning是获取不到返回值的。
     *    当然，如果你的方法本来就没有返回值，那肯定也是null咯
     *
     */


    @Test
    public void test_Aop() {
        ConfigurableApplicationContext context = SpringApplication.run(IOCTest_Aop.class);
        AService aService = context.getBean(AService.class);
        log.info("{}", aService);
        log.info("########### 正常测试 ############");
        aService.aMethod(1);
        log.info("########### 异常测试 ############");
        aService.aMethod(0);
    }

    @Test
    public void test_Aop_Around() {
        ConfigurableApplicationContext context = SpringApplication.run(IOCTest_Aop.class);
        AService aService = context.getBean(AService.class);
        log.info("{}", aService);
        log.info("########### 正常测试 ############");
        aService.aMethodAround(1);
        log.info("########### 异常测试 ############");
        aService.aMethodAround(0);
    }

    @Test
    public void test_Aop_selfCallAopMethod() {
        ConfigurableApplicationContext context = SpringApplication.run(IOCTest_Aop.class);
        AService aService = context.getBean(AService.class);
        log.info("{}", aService);
        log.info("类方法内部直接 this.被aop的方法 发生自调用导致aop失效");
        ((AServiceImpl) aService).selfCallAopMethod_Invalid();
        log.info("通过注入一个自己的代理对象, 通过代理对象调用 被aop的方法 解决了自调用aop失效");
        ((AServiceImpl) aService).selfCallAopMethod_Correct();
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
