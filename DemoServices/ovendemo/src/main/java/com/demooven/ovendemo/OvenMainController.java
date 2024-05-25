package com.demooven.ovendemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.observecore.coreobserver.myaspect.ObserveDataVessel;

@RestController
@RequestMapping("/demo")
public class OvenMainController {
    

    @GetMapping("/oven/getMenu")
    @ResponseStatus(HttpStatus.OK)
    @ObserveDataVessel(contextName = "Oven | Menu", transactionIdString = "x-transaction-id")
    public String doReceiveData(){
        //GlobalOpenTelemetry
        System.out.println("at the receive data method..");
        
        return "Oven Menu: Regular Bake, Deep Dish Bake, Thin Crust Bake, Stuffed Crust Bake";
    }

}
