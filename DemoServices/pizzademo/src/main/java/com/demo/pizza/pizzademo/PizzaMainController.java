package com.demo.pizza.pizzademo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.observecore.coreobserver.myaspect.ObserveDataVessel;

@RestController
@RequestMapping("/demo")
public class PizzaMainController {
    
    @Autowired
    PizzaService pizzaService;

    @GetMapping("/pizza/getMenu")
    @ResponseStatus(HttpStatus.OK)
    //@ObserveDataVessel(contextName = "Math | Random Number", transactionIdString = "x-transaction-id")
    @ObserveDataVessel(contextName = "Pizza | Menu", transactionIdString = "x-transaction-id")
    public String doReceiveData(){
        System.out.println("at the receive data method..");

        String completeMenu = pizzaService.getCompleteMenu();
        return completeMenu;
    }

}
