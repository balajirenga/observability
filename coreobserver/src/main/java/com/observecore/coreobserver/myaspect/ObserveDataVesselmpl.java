package com.observecore.coreobserver.myaspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.net.URL;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.checkerframework.checker.units.qual.C;
import org.checkerframework.checker.units.qual.s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;

import com.observecore.coreobserver.config.HomeObserveConfiguration;
import com.observecore.coreobserver.filter.HttpServletRequestTextMapGetter;

import io.micrometer.observation.Observation;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.TraceFlags;
import io.opentelemetry.api.trace.TraceState;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.ContextKey;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapPropagator;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import java.lang.System; // Import the System class

@Aspect
@Component
public class ObserveDataVesselmpl {

    private static final String CONTEXT = "CONTEXT";
    private static final String URL2 = "URL";
    @Autowired
    private Tracer tracer;

    @PostConstruct
    public void init() {
        System.out.println("[CoreObserver-Boot] | ObserveDataVesselmpl | BalajiAspectImpl: PostConstruct tracer is " + tracer);
    }

    @Autowired
    HomeObserveConfiguration homeObserveConfiguration;

    @Around("@annotation(observeDataVessel)")
    public Object observeCurrentMethod(ProceedingJoinPoint jointPoint, ObserveDataVessel observeDataVessel) throws Throwable {
        //Span span = createNewSpanAndClose(null, jointPoint);

        observeDataVessel.contextName();
        observeDataVessel.transactionIdString();
        Object result = null;

        System.out.println("observeDataVessel.kafkaConsumer() " + observeDataVessel.kafkaConsumer());
        if(observeDataVessel.kafkaConsumer()) {
            System.out.println("Kafka Consumer is true");
            Object[] args = jointPoint.getArgs();

            Message<String> message = (Message) args[0];

            MessageHeaders headers = message.getHeaders();
            // Accessing headers
            String traceParentValue = headers.get("traceParent", String.class);

            System.out.println("ObserveDataVesselImpl : listenWithContextTrace + Traceparent " + traceParentValue);
            Span span = listenWithContextTrace(null, traceParentValue, jointPoint);
            result = jointPoint.proceed();
            listenWithContextTrace(span, "", jointPoint);
            return result;
        }  else {
            Span span = createNewSpanAndClose(null, jointPoint, observeDataVessel);
            //Context.current().get(observeDataVessel.transactionIdString());
            String value = io.opentelemetry.context.Context.current().get(ContextKey.named(observeDataVessel.transactionIdString()));
            System.out.println("Retrived the context key in someother place : " + value);

            result = jointPoint.proceed();
            createNewSpanAndClose(span, jointPoint, observeDataVessel);

            return result;
        }   

       // return result;
    }

    public Span createNewSpanAndClose(Span span, ProceedingJoinPoint jointPoint,ObserveDataVessel observeDataVessel) {
        System.out.println("BalajiAspectImpl: doSpider");
        String methodName = jointPoint.getSignature().getName();
        Span newSpan = null;
        if (span == null) {
           
            newSpan = tracer.spanBuilder(methodName).startSpan();
            setAttributes(newSpan, jointPoint, observeDataVessel);
            newSpan.makeCurrent();
            
            String spanId = newSpan.getSpanContext().getSpanId();
            String traceId = newSpan.getSpanContext().getTraceId();

            System.out.println("===> BalajiAspectImpl On Start: [" + methodName
                    + "] TraceId : " + traceId + " Span ID:" + newSpan.getSpanContext().getSpanId() + "URL " + "");

        } else {
            span.end();
            System.out.println("===> BalajiAspectImpl On Start: [" + methodName
                    + "] Span ended ");
        }
        return newSpan;
    }

    public static final String FUNCTION_TYPE = "Function.Type";
    public static final String FUNCTION_VALUE = "Function.Value";

    private void setAttributes(Span newSpan, ProceedingJoinPoint jointPoint, ObserveDataVessel observeDataVessel) {
        String methodName = jointPoint.getSignature().getName();
        String url = myURLDetails(jointPoint);

       
        newSpan.setAttribute(FUNCTION_TYPE, observeDataVessel.functionType());
        newSpan.setAttribute(FUNCTION_VALUE, observeDataVessel.functionValue());
        newSpan.setAttribute(URL2, url);
        newSpan.setAttribute(CONTEXT, observeDataVessel.contextName());
       
    }

    public Span listenWithContextTrace(Span span,String traceParent, ProceedingJoinPoint jointPoint){
       String methodName = jointPoint.getSignature().getName();
       System.out.println("ObserveDataVesselImpl : listenWithContextTrace");

       Span parentSpan = null;
       if (span == null) {
           String[] parts = gettraceIdfromTraceParent(traceParent);

           SpanContext spanContext = SpanContext.createFromRemoteParent(
                   //traceId, 
                   //spanId,
                   parts[1],
                   parts[2], 
                   TraceFlags.getSampled(), 
                   TraceState.getDefault()
               );

           // Create a new span using the extracted context
           // Creating a Span from SpanContext
          // parentSpan = Span.wrap(spanContext);
           
           // Create a new span using the extracted context
            Span newSpan = tracer.spanBuilder(methodName)
            .setParent(Context.root().with(Span.wrap(spanContext)))
            .startSpan();

           try (Scope scope = newSpan.makeCurrent()) {
            //    System.out.println("Consumed message: " + message);
               // Process the message here
               //parentSpan.updateName(methodName);

           } finally {
               //parentSpan.end(); // End the span
           }
           return newSpan;
         }  
         else {
            span.end();
            return null;
       }
    }

    public String myURLDetails(JoinPoint joinPoint) {
        // Get method signature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // Get method
        Method method = signature.getMethod();

        // Check if the method has @GetMapping annotation
        if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping mappingAnnotation = method.getAnnotation(GetMapping.class);

            // Retrieve the path value from @GetMapping annotation
            String[] path = mappingAnnotation.value();
            System.out.println("Path: " + path[0]); // Assuming only one path is defined

            return path[0];

        }
        return "";

    }

    public void buildWithExistingContext(@Headers MessageHeaders headers) {   
        // // Tracer tracer = GlobalOpenTelemetry.getTracer("ROOT_TRACER");
        // Tracer tracer = homeObserveConfiguration.tracer();
        // TextMapPropagator propagator = GlobalOpenTelemetry.getPropagators().getTextMapPropagator();

        // Context extractedContext = propagator.extract(Context.current(),
        // headers, HttpServletRequestTextMapGetter.INSTANCE);
        // System.out.println("Extracted Content : " + extractedContext.toString());

        // System.out.println("At the buildWithExistingContext Method : " + extractedContext);
        // Span span = tracer.spanBuilder("Kafka Consumer[" + url + "]")
        //         .setSpanKind(SpanKind.SERVER)
        //         .setParent(extractedContext)
        //         .startSpan();
    }

    // public void kafkaHandlingSpan(String traceId, String spanId, String message){
    //     // Example: Creating a span using extracted traceId and spanId
    //     Span span = tracer.spanBuilder("process-message")
    //                     .setParent(Context.current().with(Span.wrap(traceId, spanId, TraceFlags.getDefault())))
    //                     .startSpan();

    //     try (Scope scope = span.makeCurrent()) {
    //         System.out.println("Consumed message: " + message);
    //     } finally { 
    //         span.end();
    //     }
    // }



        public String[] gettraceIdfromTraceParent(String traceParent){
        //  String traceparent = "00-4bf92f3577b34da6a3ce929d0e0e4736-00f067aa0ba902b7-01";
                // Split the traceparent string by '-'
                String[] parts = traceParent.split("-");

                if (parts.length < 4) {
                    System.out.println("Invalid traceparent header");
                    return null;
                }

                String version = parts[0];
                String traceId = parts[1];
                String spanId = parts[2];
                String traceFlags = parts[3];

                System.out.println("Version: " + version);
                System.out.println("Trace ID: " + traceId);
                System.out.println("Span ID: " + spanId);
                System.out.println("Trace Flags: " + traceFlags);
                return parts;
            }

}
