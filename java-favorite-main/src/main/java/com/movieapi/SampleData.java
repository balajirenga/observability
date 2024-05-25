package com.movieapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.context.Scope;


@RestController
@RequestMapping("/favorites")
public class SampleData {
    
    @GetMapping("/sampledata")
    public String sampledata(){
        System.out.println(" Hello there its been checked...");    
        
        //String SERVICE_NAME = System.getenv("OTEL_SERVICE_NAME");
        String SERVICE_NAME = "MyhomeService";

        Span span = GlobalOpenTelemetry.getTracer(SERVICE_NAME).spanBuilder("sampledata1").startSpan();
        Scope scope = span.makeCurrent();
       
        span.addEvent("a span event", Attributes
        .of(AttributeKey.longKey("someKey"), Long.valueOf(93)));

        span.addEvent("FirstRunner", Attributes.of(AttributeKey.longKey("25"), Long.valueOf("42"))).setAttribute("mylapore", "2").setAttribute("porur", "45");
        span.setStatus(StatusCode.OK);
        span.end();
        scope.close();

        Span span2 = GlobalOpenTelemetry.getTracer(SERVICE_NAME).spanBuilder("sampledata2").startSpan();
        Scope scope2 = span.makeCurrent();
        span2.addEvent("a span event", Attributes
        .of(AttributeKey.longKey("someKey"), Long.valueOf(93)));

        span2.addEvent("SecondRunner", Attributes.of(AttributeKey.longKey("56"), Long.valueOf("200"))).setAttribute("tnagar", "8").setAttribute("adyar", "100");
        span2.setStatus(StatusCode.ERROR);
        span2.end();
        
        scope2.close();
        
        System.out.println("GlobalOpenTelemetry data1 : " + GlobalOpenTelemetry.get().getTracerProvider().tracerBuilder(SERVICE_NAME).toString());

       System.out.println("Span  name :: " + span.getSpanContext().toString());

        return " Hello there.. its checked";
    }
}
