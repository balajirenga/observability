package com.kafkademo.kafkademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.observecore.coreobserver.myaspect.ObserveDataVessel;
import com.observecore.coreobserver.rest.OpenTelemetryInterceptor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class KafkaController {
    
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    OpenTelemetryInterceptor openTelemetryInterceptor;

    private static final String TOPIC = "kafkademo";

    @ObserveDataVessel(contextName = "Packaging | Kafka Producer", transactionIdString = "x-transaction-id")
    @GetMapping("publish/{message}")
    public String publishMessage(@PathVariable("message") String message) {

        String traceParent = openTelemetryInterceptor.getTraceParent();
        System.out.println("About to publish message: " + message + " to topic: " + TOPIC + " with traceParent: " + traceParent);
      
        kafkaTemplate.send(MessageBuilder.withPayload(message).setHeader("traceParent", traceParent)
        .setHeader(KafkaHeaders.TOPIC, TOPIC).build());

       // kafkaTemplate.send(TOPIC, message);
        return "Published successfully";
    }

}
