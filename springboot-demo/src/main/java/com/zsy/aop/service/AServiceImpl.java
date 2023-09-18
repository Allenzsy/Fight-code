package com.zsy.aop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Allenzsy
 * @Date 2022/8/18 22:07
 * @Description:
 */
@Slf4j
@Service
public class AServiceImpl implements AService{

    /**
     * 注入一个自己的代理对象, 解决自调用 AOP 失效的问题
     */
    @Autowired
    AService aService;

    @Override
    public void aMethod(int e) {
        log.info("AService 的实现类, 实现 aMethod 方法");
        int exception = 1 / e;
    }

    @Override
    public void aMethodAround(int e) {
        log.info("AService 的实现类, 实现 aMethodAround 方法");
        int exception = 1 / e;
    }

    public void selfCallAopMethod_Invalid() {
        aMethod(1);
    }

    public void selfCallAopMethod_Correct() {
        aService.aMethod(1);
    }
}
