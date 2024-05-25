package com.observecore.coreobserver.service;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.observecore.coreobserver.controller.CriticalBusinessService;
import com.observecore.coreobserver.myaspect.ObserveDataVessel;
import com.observecore.coreobserver.rest.CoreObserveRestClient;

@Component
public class MathsService {

    @Autowired
    CoreObserveRestClient restClient;

    @Autowired
    CriticalBusinessService criticalBusinessService;
    
    @ObserveDataVessel(contextName = "actual addition of two numbers", 
    functionType = ObserveDataVessel.FUNCTION_TYPE_PROCESSOR, functionValue = "Business Logic")
    public void add(int a, int b) {
        System.out.println("Addition of " + a + " and " + b + " is " + (a + b));
         String otLPEndPoint = "http://localhost:8082/justReceiveData";
        restClient.makeAuthenticatedRequest(otLPEndPoint, "HelloWorld".getBytes(), null);

        criticalBusinessService.doCriticalBusiness();
    }

}
