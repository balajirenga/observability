package com.homeobserver.framework.core.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class CarrierSettingValues {
    
    public void beforeAnyMethod(JoinPoint joinPoint, Carrier carrier){
        long initTime = System.currentTimeMillis();
        carrier.setInitTime(initTime);
        carrier.setClassName(joinPoint.getSignature().getDeclaringType().toGenericString());
        carrier.setMethodName(joinPoint.getSignature().getName());
    }

    public void afterAnyMethod(JoinPoint joinPoint, Carrier carrier){

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - carrier.getInitTime();

        carrier.setEndTime(endTime);
        carrier.setExecutionTime(executionTime);
       // carrier.toString();
    }

     public void myGetAnnotationDetails(JoinPoint joinPoint, Carrier carrier) {
        // Get method signature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        
        // Get method
        Method method = signature.getMethod();
        carrier.setMethod(method);
        // Check if the method has @GetMapping annotation
        if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping mappingAnnotation = method.getAnnotation(GetMapping.class);
            
            // Retrieve the path value from @GetMapping annotation
            String[] path = mappingAnnotation.value();
            System.out.println("Path: " + path[0]); // Assuming only one path is defined
            carrier.setUrl(path[0]);
            // Retrieve method parameters
            String[] parameterNames = signature.getParameterNames();
            Object[] args = joinPoint.getArgs();
            
            // Log method parameters
            for (int i = 0; i < parameterNames.length; i++) {
                System.out.println("Parameter " + parameterNames[i] + ": " + args[i]);
            }
        }
    
    }
}
