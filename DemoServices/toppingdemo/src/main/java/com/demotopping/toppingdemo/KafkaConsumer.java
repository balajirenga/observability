package com.demotopping.toppingdemo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import com.observecore.coreobserver.myaspect.ObserveDataVessel;

@Component
public class KafkaConsumer {

    // @KafkaListener(topics = AppConfig.HELLOWORLD_TOPIC, groupId = "packagingdemo")
    // public void listen(String message, @Headers MessageHeaders headers) {
    //     System.out.println("At the consume method..");
    //     System.out.println("Consumed message: " + message);
    //     System.out.println("Headers: " + headers);
    // }

   
    @ObserveDataVessel(contextName = "Packaging | Kafka Consumer", transactionIdString = "x-transaction-id" ,kafkaConsumer = true)
    @KafkaListener(topics = AppConfig.HELLOWORLD_TOPIC, groupId = "toppingdemo")
    public void listen(String message, @Headers MessageHeaders headers){
        System.out.println(">>> At the consume method..");
        System.out.println(">>> Consumed message: " + message);
        System.out.println(">>> Headers: " + headers);
    }

}
