package com.testdrive.drivers;

import org.springframework.stereotype.Component;

import com.observecore.coreobserver.myaspect.ObserveDataVessel;

@Component
public class MyMath {
    

    @ObserveDataVessel(contextName = "Math | Random Number", transactionIdString = "x-transaction-id")
    public void addNumber(int a, int b){
        System.out.println("Adding two numbers... " + (a+b));
    }
}
