package com.homeobserver.framework.core.handler;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import io.micrometer.common.KeyValue;
import io.micrometer.common.KeyValues;
import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.tracing.SamplerFunction;
import io.opentelemetry.api.GlobalOpenTelemetry;


import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;


public class HomePulseHandler implements ObservationHandler<Observation.Context> {
   
    // @Autowired 
    // io.micrometer.tracing.Tracer tracer;

    //String traceId = tracer.currentSpan().context().traceId();
    String traceId = "yet toconvert";
    String spanId = "yet toconvert";
    //SpanBuilder spanBuilder = GlobalOpenTelemetry.getTracer("PizzaPrepare").spanBuilder("Prepare Toppings");
    SpanBuilder spanBuilder = null;
    Tracer tracerObj = GlobalOpenTelemetry.getTracer("PizzaPrepare");
    
    Span span = null;
   // public OutputDom outputDom = null;

    MeterRegistry coremeterRegistry = new SimpleMeterRegistry();
    Timer.Sample eventSample = null;
    static int counter = 0;

    public HomePulseHandler(){
        // Span span = GlobalOpenTelemetry.getTracer("check..").spanBuilder("doSomeSample1").startSpan();
        // Scope scope = span.makeCurrent();
        // span.addEvent("ado some sampleevent", Attributes
        // .of(AttributeKey.longKey("sampleKey"), Long.valueOf(93)));
        // span.addEvent("FirstRunner", Attributes.of(AttributeKey.longKey("25"), Long.valueOf("42"))).setAttribute("simei", "2").setAttribute("changi", "45");
        // System.out.println("Trace id " + span.getSpanContext().getTraceId());
       // OutputDom outputDom = new OutputDom();
       // this.outputDom= outputDom;
    }

    Scope scope = null;
    static int i=0;

    @Override
    public void onStart(Observation.Context context) {

        System.out.println("All Key Values: " + context.getAllKeyValues());

        //registry.getCurrentObservation().getContext().put("MethodName", "pizzprepare");
       // String methodName = TempClass.someValue;

       //spanBuilder = tracerObj.spanBuilder("Prepare Toppings [" + counter++ + "]");
       spanBuilder = tracerObj.spanBuilder("temp");
        span = spanBuilder.startSpan();
        scope = span.makeCurrent();
        traceId = span.getSpanContext().getTraceId();
        spanId = span.getSpanContext().getSpanId();
      //  span.addEvent("do some sampleevent", Attributes.of(AttributeKey.longKey("sampleKey"), Long.valueOf(93)));
        
        eventSample = Timer.start(coremeterRegistry);
    
     //  outputDom.setTraceId(traceId);
        
        System.out.println(" All Key Values " + context.getAllKeyValues());

        System.out.println("traceId " + traceId + " spanid " + spanId);


  

        System.out.println("@Handler: Start: " +  context.getName() +   "***** Object name " + "data: " + context.get(String.class) + ": " + "[" +traceId + ": " + spanId + "]" );
       
        // System.out.println();  
        Span spanTopping = span.addEvent("Topping " + counter++);
        span.makeCurrent();
        spanTopping.setAttribute("cheese", "parmessan-"+(i++));
        spanTopping.setAttribute("tomato", "brazil"+(i++));
        
       // spanTopping.storeInContext(context);
        

       // LongTaskTimer.Sample sample = context.get(LongTaskTimer.Sample.class);
       // System.out.println("@Handler: Start: " + context.getAllKeyValues());
        //System.out.println("@Handler: Start: " + sample.duration(TimeUnit.SECONDS));
        // System.out.println("-----");
        // Iterable<KeyValue> iterable = context.getLowCardinalityKeyValues();
        // for (KeyValue element : iterable) {
        //     System.out.println("ALLKEYSET: " + element.getKey() + ": " + element.getValue()+ ":" + element.getClass().getName());
        // }
        // System.out.println("-----");  

        // context.put("startTime", System.nanoTime());
    }

    @Override
    public void onError(Observation.Context context) {
      //  System.out.println("ERROR " + "data: " + context.get(String.class) + ", error: " + context.getError());
      //  System.out.println("***** error Object name " + this.hashCode() + "---" + this.toString() + ": " + traceId);
    }

    @Override
    public void onEvent(Observation.Event event, Observation.Context context) {
      //  System.out.println("EVENT " + "event: " + event + " data: " + context.get(String.class));
       // System.out.println("@Handler: Event: "  + event.getName() + "***** Object name " + this.hashCode() + "---" + this.toString() + ": " + traceId);
    }

    @Override
    public void onStop(Observation.Context context) {
        span.setStatus(StatusCode.OK);
        span.end();
      //  scope.close();
    //   eventSample.stop(Timer.builder("my.timer").register(coremeterRegistry));
      //  System.out.println("STOP  " + "data: " + context.get(String.class));
     //  System.out.println("@Handler: Stop: "  + context.getName() + "***** Object name " + this.hashCode() + "---" + this.toString() + ": " + "[" +traceId + ": " + spanId + "]" );
    
      //  LongTaskTimer.Sample sample = context.get(LongTaskTimer.Sample.class);
     //   System.out.println("@Handler: Stop: LongTaskTimer" + sample.duration(TimeUnit.SECONDS) + ":: " + sample.duration(TimeUnit.MILLISECONDS));
      //  System.out.println("-----");

     //   Long startTime = context.get("startTime");
    //    long duration = System.nanoTime() - startTime;
        // nanos to seconds, you can also use TimeUnit but that will round
    //    System.out.println(duration / 1E9);

    //    System.out.println(" Time " + coremeterRegistry.toString());
        List<Meter> list = coremeterRegistry.getMeters();
     //   System.out.println("Size:: " + list.size());
        for (Meter meter : list) {
      //     System.out.println(" Under the list :: " + meter.getId().getName() + "-" + meter.measure().toString());
      //     System.out.println(" Tags.. " + meter.getId().getTags());
        }

    }

    @Override
    public boolean supportsContext(Observation.Context handlerContext) {
        // you can decide if your handler should be invoked for this context object or
        // not
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }


}