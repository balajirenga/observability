package com.homeobserver.framework.core.handler;

import org.springframework.stereotype.Component;

import com.homeobserver.framework.core.aspect.Carrier;

import io.micrometer.common.KeyValues;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.tracing.annotation.NewSpan;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;

@Component
public class BalajiHandler implements ObservationHandler<Observation.Context> {

       // private final Tracer tracer;
    // private Carrier carrier;

    public BalajiHandler() {
        // this.tracer = tracer;
        // this.carrier = carrier;
        System.out.println("New BalajiHandler created");
    }   
    private static int i =0;

    @Override
    //@NewSpan("BalajiHandler")
    public void onStart(Observation.Context context) {
        Tracer tracer = GlobalOpenTelemetry.getTracer(context.getContextualName());
        Span span = tracer.spanBuilder(context.getContextualName()).startSpan();
      
        Scope scope = span.makeCurrent();
        System.out.println("===> On Start:   Contextual Name: " + context.getContextualName());
        
        String spanId = span.getSpanContext().getSpanId();
        String traceId = span.getSpanContext().getTraceId();
        System.out.println("===> BalajiAspectImpl On Start: [" +context.getContextualName() 
        + "] TraceId : " + traceId + " Span ID:" + span.getSpanContext().getSpanId() + "URL " + "");

        // if (context.getContextualName().equalsIgnoreCase("doSpider")) {
        //     System.out.println("++++++ > BalajiAspectImpl On Start: The Parent GUY Span ID is : " + spanId);
        //     span.setAttribute("URL", "someURL");
        //     span.updateName(context.getContextualName());
        // } else {
        //     System.out.println("++++++ > BalajiAspectImpl On Start: The CHILD GUY Span ID is : " + spanId);
        // }
    }
    
    // @Override
    // public void onStop(Observation.Context context) {
    //     System.err.println("Onstop.... BalajiHandler");
    //     Span span = Span.fromContext(Context.current());
    //     span.setStatus(StatusCode.OK);
    //     // Your onStop logic here
    //     // System.out.println("On Stop: <pending logic> " + context);
    //     // End the span

    //     span.end();
    // }

    @Override
    public void onStop(Observation.Context context) {
        System.out.println("BalajiAspectImpl On Stop: " + context.getContextualName());
       // Span span = Span.current();
        Span span = Span.fromContext(Context.current());
        Scope scope = Span.current().makeCurrent();
        String spanId = span.getSpanContext().getSpanId();
        String traceId = span.getSpanContext().getTraceId();
        System.out.println("===> BalajiAspectImpl On Stop: [" +context.getContextualName() 
        + "] TraceId : " + traceId + " Span ID:" + span.getSpanContext().getSpanId() + "URL " + "");

        // How to retrieve the span and stop it ?
        span.setStatus(StatusCode.OK);

        scope.close();;
        span.end();
    }

    @Override
    public boolean supportsContext(io.micrometer.observation.Observation.Context arg0) {
      return true;
    }


}
