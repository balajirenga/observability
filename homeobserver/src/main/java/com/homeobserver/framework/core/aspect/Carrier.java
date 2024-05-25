package com.homeobserver.framework.core.aspect;

import lombok.Getter;
import lombok.Setter;
import java.lang.reflect.Method;

public class Carrier {
    @Getter @Setter private String className;
    @Getter @Setter private String methodName;
    @Getter @Setter private String valuesString;
    @Getter @Setter private long initTime;
    @Getter @Setter private long executionTime;
    @Getter @Setter private long endTime;
    @Getter @Setter private String url;
    @Getter @Setter private String exceptionDetails;
    @Getter @Setter private String contextName;
    @Getter @Setter private Method method;

    @Override
    public String toString() {
        System.out.println("Method Name " + methodName);
        System.out.println("Method Declaring type " + className);
        
        System.out.println("valuesString " + valuesString);
        System.out.println("initTime " + initTime);
        System.out.println("endTime " + endTime);
        System.out.println("executionTime " + executionTime);

        System.out.println("url " + url);
        System.out.println("exceptionDetails " + exceptionDetails);
    
        return super.toString();
    }
}
