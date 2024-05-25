package com.homeobserver.framework.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.homeobserver.framework.core.myaspect.BalajiAspect;

@Component
public class DoRandom {
    
    @Autowired
    BalajiRestClient balajiRestClient;

    @BalajiAspect()
    public void returnARandomNumber() {
        System.out.println("At the place of return Random Number...");
        balajiRestClient.makeAuthenticatedRequest("hello".getBytes(), "nothing");
        //return (int) (Math.random() * 100);
    }
}
