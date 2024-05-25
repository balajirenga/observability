package com.democheese.cheesedemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.observecore.coreobserver.myaspect.ObserveDataVessel;

@RestController
@RequestMapping("/demo")
public class CheeseMainController {
    

    @GetMapping("/cheese/getMenu")
    @ResponseStatus(HttpStatus.OK)
    @ObserveDataVessel(contextName = "Cheese | Menu", transactionIdString = "x-transaction-id")
    public String doReceiveData(){
        //GlobalOpenTelemetry
        System.out.println("at the receive data method..");
        
        return "Cheese Menu: Mozzarella, Cheddar, Parmesan, Gouda, Blue Cheese, Feta, Goat Cheese, Provolone, Swiss Cheese";
    }

}
