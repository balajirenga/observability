package com.homeobserver.framework.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeobserver.framework.core.myaspect.BalajiAspect;

@Component
public class DoSomething {
    
    @Autowired
    DoRandom doRandomObject;
    
    @BalajiAspect()
    public void doSomething() {
        System.err.println("doSomething here... '");
        doRandomObject.returnARandomNumber();
    }


    
}
