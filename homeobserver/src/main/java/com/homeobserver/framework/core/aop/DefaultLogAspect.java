package com.homeobserver.framework.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import io.micrometer.observation.ObservationConvention;
import io.micrometer.observation.aop.ObservedAspect;
import io.micrometer.observation.aop.ObservedAspect.ObservedAspectContext;
import jakarta.annotation.PostConstruct;


@Aspect
@Component
public class DefaultLogAspect extends AbstractLogAspect {

    // after intiialize call the method
    @PostConstruct
    public void init() {
        System.out.println("DefaultLogAspect Aspect is initialized..");
    }


    @Override
    @Around("@annotation(com.homeobserver.framework.core.aop.LogInfo)" +
            "|| @within(com.homeobserver.framework.core.aop.LogInfo)")     
    //@Around("@annotation(logInfo)") 
    public Object logInfoAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("DefaultLogAspect || At the aspect...");

        

        return super.logInfoAround(joinPoint);
    }

//     @Around("@annotation(homeObserver)")
//    public Object justchecking(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("DefaultLogAspect || justchecking || enableRestCallLogs --> At the aspect...");
//        return super.logInfoAround(joinPoint);
//    }

}