package com.country.annotate;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EnableHomeTraceCollector {
    
    @Around("@annotation(HomeTraceCollector)")
    //@Before("@annotation(HomeTraceCollector)")
    public Object collectData(ProceedingJoinPoint joinPoint){
        long initTime = System.currentTimeMillis() ;
        Object proceed =null;
        try {
            proceed = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - initTime;
            System.out.println("Method Signature is " + joinPoint.getSignature().toLongString());
            System.out.println("Method executed in " + executionTime);
            System.out.println("Method Input Request " + joinPoint.getArgs()[0]);
            System.out.println("Output response: " + proceed);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return proceed;
    }
}
