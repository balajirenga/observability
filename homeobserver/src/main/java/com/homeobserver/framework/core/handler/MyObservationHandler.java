package com.homeobserver.framework.core.handler;

import com.homeobserver.framework.core.aspect.Carrier;

import io.micrometer.common.KeyValues;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.proto.common.v1.KeyValue;
import io.opentelemetry.semconv.trace.attributes.SemanticAttributes;

public class MyObservationHandler implements ObservationHandler<Observation.Context> {

    // private final Tracer tracer;
    // private Carrier carrier;

    public MyObservationHandler() {
        // this.tracer = tracer;
        // this.carrier = carrier;
        System.out.println("New MyObservationHandler created");
    }

    Scope scope = null;

    @Override
    public void onStart(Observation.Context context) {
        KeyValues keyValues = context.getAllKeyValues();
        System.out.println("===> On Start:   keyValues " + keyValues.toString());

        Carrier carrier = (Carrier) ThreadLocalBag.getThreadLocalValue();

        Tracer tracer = GlobalOpenTelemetry.getTracer(carrier.getContextName());

        System.out.println("===> On Start:   Contextual Name: " + context.getContextualName());

        Span span = null;
        keyValues.iterator().forEachRemaining(keyValue -> {
            System.out.println("===> On Start:  Key: " + keyValue.getKey() + " Value: " + keyValue.getValue());
            // setMe();
            Span span2 = tracer.spanBuilder(keyValue.getValue()).startSpan();
        });

        

        // while(keyValues.iterator().hasNext()){
        // io.micrometer.common.KeyValue keyValue = keyValues.iterator().next();
        // System.out.println("===> On Start: Key: " + keyValue.getKey() + " Value: " +
        // keyValue.getValue());
        // span = tracer.spanBuilder(keyValue.getValue()).startSpan();
        // }

        // Span span = tracer.spanBuilder(carrier.getMethodName()).startSpan();
        Scope scope = span.makeCurrent();
        span.setAttribute("URL", carrier.getUrl());

        System.out.println("===> On Start:  Span ID:" + span.getSpanContext().getSpanId() + "URL " + carrier.getUrl());

        System.out.println(
                "===> On Start:   Method name ===>" + carrier.getMethodName() + " carrier Obj " + carrier.hashCode());
        System.out.println("===> On Start:  Traceid " + span.getSpanContext().getTraceId());

    }

    private void setMe() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'setMe'");
    }

    @Override
    public void onStop(Observation.Context context) {
        System.out.println("MyObservationHandler On Stop: " + context);
        // Retrieve the span from the current context
        Span span = Span.fromContext(Context.current());
        span.setStatus(StatusCode.OK);
        // Your onStop logic here
        // System.out.println("On Stop: <pending logic> " + context);
        // End the span

        span.end();
    }

    @Override
    public boolean supportsContext(io.micrometer.observation.Observation.Context arg0) {
        return true;
    }
}