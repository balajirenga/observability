package com.demopackaging.packagingdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.observecore.coreobserver.myaspect.ObserveDataVessel;
import com.observecore.coreobserver.rest.OpenTelemetryInterceptor;

import org.springframework.messaging.Message;


@Component
public class KafkaProducer {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	OpenTelemetryInterceptor openTelemetryInterceptor;

    @ObserveDataVessel(contextName = "Packaging | Kafka Producer", transactionIdString = "x-transaction-id", kafkaConsumer = true)
    public void sendMessage(String topic, String payload) {
        System.out.println("Producing message: " + payload);

        String traceParent = openTelemetryInterceptor.getTraceParent();
        System.out.println("At the producer, traceParent: " + traceParent);

        Message<String> message = MessageBuilder.withPayload(payload)
        .setHeader(KafkaHeaders.TOPIC, topic)
        .setHeader("traceParent", traceParent)
        .build();

        kafkaTemplate.send(message);

        System.out.println("Produced message: " + message);
    }

}
