package com.observecore.coreobserver.controller;

import org.springframework.stereotype.Component;

import com.observecore.coreobserver.myaspect.ObserveDataVessel;

@Component
public class CriticalBusinessService {
    
    @ObserveDataVessel(contextName = "somecriticalbusiness", 
    functionType = ObserveDataVessel.FUNCTION_TYPE_PROCESSOR, functionValue = "Business Logic")
    public void doCriticalBusiness() {
        System.out.println("Critical Business Service is running....");
    }
}
