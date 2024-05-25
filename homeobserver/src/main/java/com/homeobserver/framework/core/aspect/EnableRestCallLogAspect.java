package com.homeobserver.framework.core.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;

@Aspect
@Component
public class EnableRestCallLogAspect {

   
    // after intiialize call the method
    @PostConstruct
    public void init() {
        System.out.println("EnableRestCallLogAspect Aspect is initialized..");
    }

    @Autowired
    CarrierSettingValues carrierSettingValues;

    //@Around("execution(* com.homeobserver.framework.core.controller.*.*(..))")
    @Around("@annotation(enableRestCallLogs)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, EnableRestCallLogs enableRestCallLogs) throws Throwable {

        //System.out.println("On the aspect call..");
        Carrier carrier = new Carrier();

        Object proceed = null;
        try {

            carrierSettingValues.beforeAnyMethod(joinPoint, carrier);
            proceed = joinPoint.proceed();
            carrier.setValuesString(enableRestCallLogs.valuesString());
            carrierSettingValues.myGetAnnotationDetails(joinPoint, carrier);
            carrierSettingValues.afterAnyMethod(joinPoint, carrier);

            //System.out.println("Output Response : " + proceed);

        } catch (Exception e) {
            carrier.setExceptionDetails(e.getMessage());
            System.out.println("Exception occured : " + e.getMessage());
        }
        
        return proceed;
    }

    
}
