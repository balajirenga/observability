package com.kafkademo.kafkademo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.observecore.coreobserver.myaspect.ObserveDataVessel;

@Component
public class KafkaConsumer {
    
    @ObserveDataVessel(contextName = "Packaging | Kafka Consumer", transactionIdString = "balajitd" ,kafkaConsumer = true)
    @KafkaListener(topics = "kafkademo", groupId = "kafkademo")
    public void consume(Message<String> message) {
        System.out.println("[kafkademo] Consumed message: " + message);

        MessageHeaders headers = message.getHeaders();
        String payloadMessage = message.getPayload();
        
        // Accessing headers
        String traceParentValue = headers.get("traceParent", String.class);
        theKafkaConsumerMethod(payloadMessage, traceParentValue);
        
    }

    
    public void theKafkaConsumerMethod(String message, String traceParent) {
        System.out.println("This is the KafkaConsumer method");
    }

}
