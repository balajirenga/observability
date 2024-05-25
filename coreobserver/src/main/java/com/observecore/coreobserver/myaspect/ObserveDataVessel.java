package com.observecore.coreobserver.myaspect;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ObserveDataVessel {
    String contextName() default "";
    String transactionIdString() default "";
    String transactionIdLocation() default "Header";
    @Deprecated boolean kafkaConsumer() default false;
    String traceParent() default "";

    String functionType() default "Function";
    String functionValue() default "Function";

    public static final String FUNCTION_TYPE_CONSUMER = "Function.Consumer";
    public static final String FUNCTION_TYPE_INTEGRATOR = "Function.Integrator";
    public static final String FUNCTION_TYPE_PROCESSOR = "Function.Processor";
    public static final String FUNCTION_TYPE_DATA = "Function Data";

    public static final String FUNCTION_VALUE_CONSUMER_HTTP = "Function.Consumer.HTTP";
    public static final String FUNCTION_VALUE_CONSUMER_KAFKA = "Function Consumer Kafka";
    public static final String FUNCTION_VALUE_CONSUMER_RABBITMQ = "Function Consumer RabbitMQ";
    public static final String FUNCTION_VALUE_INTEGRATOR_HTTP = "Function Integrator HTTP";
    public static final String FUNCTION_VALUE_INTEGRATOR_KAFKA = "Function Integrator Kafka";
    public static final String FUNCTION_VALUE_DATA_ORACLE = "Function Data Oracle";
    public static final String FUNCTION_VALUE_DATA_MYSQL = "Function Data MySQL";
    public static final String FUNCTION_VALUE_DATA_MONGODB = "Function Data MongoDB";


}