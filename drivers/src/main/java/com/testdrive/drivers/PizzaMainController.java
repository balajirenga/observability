package com.testdrive.drivers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.observecore.coreobserver.myaspect.ObserveDataVessel;

@RestController
public class PizzaMainController {
    
    @Autowired
    MyMath myMath;

    @GetMapping("/drivers/pizza/getMenu")
    @ResponseStatus(HttpStatus.OK)
    //@ObserveDataVessel(contextName = "Math | Random Number", transactionIdString = "x-transaction-id")
    @ObserveDataVessel(contextName = "Pizza | Menu", transactionIdString = "x-transaction-id")
    public String doReceiveData(){
        //GlobalOpenTelemetry
        System.out.println("at the receive data method..");

        myMath.addNumber(0, 0);
        
        return "Main Menu: Margherita, Pepperoni, BBQ Chicken, Hawaiian, Meat Lovers, Veggie Supreme, Seafood, Cheeseburger, and more!";
    }

}
