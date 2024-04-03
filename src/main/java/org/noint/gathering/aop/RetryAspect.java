package org.noint.gathering.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.noint.gathering.annotation.Retry;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 1)	//@Transactional annotation보다 먼저 실행되어야하므로.
@Aspect
@Component
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        int maxRetry = retry.value();

        Exception exceptionHolder = null;
        for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try{
                return joinPoint.proceed();
            }catch (Exception e){
                log.error("[retry] try count ={}/{}", retryCount, maxRetry);
                exceptionHolder = e;
            }
        }
        throw exceptionHolder;
    }
}
