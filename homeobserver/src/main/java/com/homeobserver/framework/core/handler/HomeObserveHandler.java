package com.homeobserver.framework.core.handler;

import io.micrometer.observation.Observation;
import io.micrometer.observation.Observation.Context;
import io.micrometer.observation.ObservationHandler;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;

public class HomeObserveHandler implements ObservationHandler<Observation.Context> {

    @Override
    public void onStart(Context arg0) {
        System.out.println("On Start: " + arg0);

        SpanBuilder spanBuilder = null;
        Tracer tracerObj = GlobalOpenTelemetry.getTracer("PizzaPrepare");

        // spanBuilder = tracerObj.spanBuilder("Prepare Toppings [" + counter++ + "]");
        spanBuilder = tracerObj.spanBuilder("check it out..");
        Span span = spanBuilder.startSpan();
        Scope scope = span.makeCurrent();

        String traceId = span.getSpanContext().getTraceId();
        String spanId = span.getSpanContext().getSpanId();

        System.out.println("traceId " + traceId + " spanid " + spanId);

       
    }

    @Override
    public void onStop(Context arg0) {
        System.out.println("On Stop: " + arg0);
        //.setStatus(StatusCode.OK);
       // span.end();
    }

    @Override
    public boolean supportsContext(Context arg0) {
       return true;
    }
    
}
