package com.homeobserver.framework.core.service;

import org.springframework.stereotype.Component;

import com.homeobserver.framework.core.aspect.EnableHomeObserver;

@Component
public class MathsService {

    //@EnableHomeObserver(contextName = "MathsService")
    public int anotherSum(int a, int b) {
        a = a +5;
        System.err.println("In the anotherSum Method" + (a+b));
        return a + b;
    }

}