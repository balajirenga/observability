package com.demopackaging.packagingdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/demo")
public class PackagingMainController {
    
    @Autowired
    KafkaProducer kafkaProducer;

    @GetMapping("/packaging/getMenu")
    @ResponseStatus(HttpStatus.OK)
    //@ObserveDataVessel(contextName = "Packaging | Menu", transactionIdString = "x-transaction-id")
    public String doReceiveData(){

        //GlobalOpenTelemetry
        System.out.println("at the receive data method..");
        kafkaProducer.sendMessage("helloworld", "Hello World from the Packaging Service");

        return "Packaging Menu: Standard Box, Eco-friendly Box, Family Pack Box, Party Pack Box";
    }

}
