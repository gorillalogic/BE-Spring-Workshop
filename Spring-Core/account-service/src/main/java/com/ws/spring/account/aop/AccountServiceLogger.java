package com.ws.spring.account.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Aspect
@Component
public class AccountServiceLogger {


    @Before("execution(public * com.ws.spring.account.service..get*(*))")
    public void before(JoinPoint point) {
        String name = point.getSignature().getName();
        Object value = point.getArgs()[0];
        log.info("@Before - Service inbound call to [{}] with param [{}]", name, value);
    }

    @AfterReturning(value = "execution(public * com.ws.spring.account.service..get*(*))", returning = "optional")
    public void afterReturning(JoinPoint point, Optional optional) {
        String name = point.getSignature().getName();
        log.info("@AfterReturning - Service outbound call from [{}] with result [{}]", name, optional.isEmpty() ? "Empty" : optional.get());
    }

    @AfterThrowing(value = "execution(public * com.ws.spring.account.service..get*(*))", throwing = "ex")
    public void afterThrowing(JoinPoint point, Exception ex) {
        String name = point.getSignature().getName();
        log.info("@AfterThrowing - Service call to [{}] throws [{}]", name, ex);
    }

    @After(value = "execution(public * com.ws.spring.account.service..get*(*))")
    public void after(JoinPoint point) {
        String name = point.getSignature().getName();
        log.info("@After - Service call to [{}] completed", name);
    }

    @Around(value = "execution(public * com.ws.spring.account.service..get*(*))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String name = point.getSignature().getName();
        log.info("@Around - Service call to [{}] starting", name);

        Object result = point.proceed();
        log.info("@Around - Service call to [{}] ending", name);

        return result;
    }

}
