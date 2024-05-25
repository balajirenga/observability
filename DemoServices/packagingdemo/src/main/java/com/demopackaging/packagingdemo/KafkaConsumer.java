package com.demopackaging.packagingdemo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.MessageHeaders;

import org.springframework.stereotype.Component;

import com.observecore.coreobserver.myaspect.ObserveDataVessel;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "helloworld", groupId = "packagingdemo")
    @ObserveDataVessel(contextName = "Packaging | Kafka Consumer", transactionIdString = "x-transaction-id" ,kafkaConsumer = true)
    public void listen(String message, @Headers MessageHeaders headers) {
        System.out.println("At the consume method..");
        System.out.println("Consumed message: " + message);
        System.out.println("Headers: " + headers);
    }

    // @KafkaListener(topics = "helloworld", groupId = "packagingdemo")
    // @ObserveDataVessel(contextName = "Packaging | Kafka Consumer", transactionIdString = "x-transaction-id" ,kafkaConsumer = true)
    // public void listenWithContextTrace(String message, @Headers MessageHeaders headers){
    //      // Assume traceId and spanId are sent as headers
    //     String traceParent = headers.get("traceparent", String.class);

    //     // System.out.println("CoreObserveFilter: doFilter Method.... " + homeObserveConfiguration.tracer());
    //     // // Tracer tracer = GlobalOpenTelemetry.getTracer("ROOT_TRACER");
    //     // Tracer tracer = homeObserveConfiguration.tracer();

    //     String[] parts = gettraceIdfromTraceParent(traceParent);

    //     SpanContext spanContext = SpanContext.createFromRemoteParent(
    //             //traceId, 
    //             //spanId,
    //             parts[1],
    //             parts[2], 
    //             TraceFlags.getSampled(), 
    //             TraceState.getDefault()
    //         );

    //         // Create a new span using the extracted context
    //     // Creating a Span from SpanContext
    //     Span parentSpan = Span.wrap(spanContext);

    //     try (Scope scope = parentSpan.makeCurrent()) {
    //         System.out.println("Consumed message: " + message);
    //         // Process the message here
    //         parentSpan.updateName("kafka-consumer-Topic[helloworld]");

    //     } finally {
    //         parentSpan.end(); // End the span
    //     }
    // }

    // @KafkaListener(topics = "helloworld", groupId = "packagingdemo")
    // @ObserveDataVessel(contextName = "Packaging | Kafka Consumer", transactionIdString = "x-transaction-id")
    // public void listen(String message,  // This is the message payload
    //                    @Headers MessageHeaders headers,  // Access all headers
    //                    @Header("traceparent") String customHeader) {  // Access specific header
    //     System.out.println("Consumed message: " + message);
    //     System.out.println("Headers: " + headers);
    //     System.out.println("Custom Header: " + customHeader);
    // }
    

    // public String[] gettraceIdfromTraceParent(String traceParent){
    //   //  String traceparent = "00-4bf92f3577b34da6a3ce929d0e0e4736-00f067aa0ba902b7-01";
    //         // Split the traceparent string by '-'
    //         String[] parts = traceParent.split("-");

    //         if (parts.length < 4) {
    //             System.out.println("Invalid traceparent header");
    //             return null;
    //         }

    //         String version = parts[0];
    //         String traceId = parts[1];
    //         String spanId = parts[2];
    //         String traceFlags = parts[3];

    //         System.out.println("Version: " + version);
    //         System.out.println("Trace ID: " + traceId);
    //         System.out.println("Span ID: " + spanId);
    //         System.out.println("Trace Flags: " + traceFlags);
    //         return parts;
    //     }


}
