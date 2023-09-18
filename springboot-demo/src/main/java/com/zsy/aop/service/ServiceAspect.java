package com.zsy.aop.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author Allenzsy
 * @Date 2022/8/18 22:06
 * @Description:
 */
@Slf4j
@Aspect
@Component
public class ServiceAspect {

    /**
     * 当使用多个通知时可能需要重复写切点, 此时可以随便写个方法名, 然后将公共的切点写在它上面, 就可以代表这一长串了
     * "*": 代表任意返回值
     * "(..)": 代表任意参数
     */
    @Pointcut("execution(* com.zsy.aop.service.AServiceImpl.aMethod(..))")
    public void aMethod(){}

    @Pointcut("execution(* com.zsy.aop.service.AServiceImpl.aMethodAround(..))")
    public void aMethodAround(){}

    @Before("aMethod() || aMethodAround()")
    public void before() {
        log.info("before 前置通知, 在代理对象的方法前调用");
    }

    @After("aMethod() || aMethodAround()")
    public void after() {
        log.info("after 后置通知, 在代理对象的方法后调用, 无论正常还是异常");
    }

    @AfterReturning("aMethod() || aMethodAround()")
    public void afterReturning() {
        log.info("afterReturning 返回通知, 在代理对象的方法正常返回后调用");
    }

    @AfterThrowing("aMethod() || aMethodAround()")
    public void afterThrowing() {
        log.info("afterThrowing 异常通知, 在代理对象的方法抛出异常后调用");
    }

    @Around("aMethodAround()")
    public void around(ProceedingJoinPoint joinPoint) {
        log.info("around before 环绕通知调用对象方法前, 在 before 之前");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        log.info("around after 环绕通知调用对象方法后, 在 after 之后");
    }
}
