package com.demotopping.toppingdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.observecore.coreobserver.myaspect.ObserveDataVessel;

@RestController
@RequestMapping("/demo")
public class ToppingMainController {

    @Autowired
    KafkaProducer kafkaProducer;

    @GetMapping("/topping/getMenu")
    @ResponseStatus(HttpStatus.OK)
    @ObserveDataVessel(contextName = "Packaging | Menu", transactionIdString = "x-transaction-id")
    public String doReceiveData(){

        //GlobalOpenTelemetry
        System.out.println("at the receive data method..");

        kafkaProducer.sendMessage(AppConfig.HELLOWORLD_TOPIC, "Hello World from the Topping Service");

        return "Toppings Menu: Pepperoni, Sausage, Mushrooms, Onions, Green Peppers, Black Olives, Pineapple, Ham, Bacon, Spinach, Artichokes, Jalapenos, Tomatoes, Chicken, Beef";
    }

}
