package com.country.countryprj;



import java.util.List;

import org.springframework.context.annotation.Bean;

import com.country.Observation.BasicSpringSetup;
import com.country.Observation.CustomHandler;
import com.country.Observation.HomePulseHandler;
import com.country.output.OutputDom;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationPredicate;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.ObservationTextPublisher;
import io.micrometer.observation.ObservationRegistry.ObservationConfig;



public class SimpleProgram {
    

    private List<Meter> list;

    // public static void main(String[] args) {
    //     SimpleProgram simpleObj = new SimpleProgram();
    //     simpleObj.doObserve();
    // }

    /**
     * @return
     */
    public OutputDom doObserve(){
        // //System.out.println("Started SimpleProgram Main method");

        MeterRegistry meterRegistry = new SimpleMeterRegistry();

        ObservationRegistry registry = ObservationRegistry.create();      
        // CustomHandler customHandler =  new CustomHandler();
        // registry.observationConfig().observationHandler(customHandler)
        // .observationHandler(new ObservationTextPublisher(System.out::println))
        // .observationHandler(new DefaultMeterObservationHandler(meterRegistry));

        HomePulseHandler homePulseObj = new HomePulseHandler();
        registry.observationConfig().observationHandler(homePulseObj)
        .observationHandler(new ObservationTextPublisher(System.out::println))
        .observationHandler(new DefaultMeterObservationHandler(meterRegistry));

        // ObservationTextPublisher
         //DefaultMeterObservationHandler
       
       
        // Timer.Sample sample = Timer.start(meterRegistry);
    
       // MeterObservationHandler handler1 = new MyMeterObservationHandler();

        // System.out.println("Sample : " + sample.toString());

        try {
           // Observation.Context context = new Observation.Context().put(String.class, "test");
            // using a context is optional, so you can call createNotStarted without it:
            // Observation.createNotStarted(name, registry)
          //  Observation.createNotStarted("my.operation", () -> context, registry).observe(() -> doSomething());
          
            //Observation.start("my.operation1", registry).observe(() -> doSomething());
           // registry.getCurrentObservation().getContext().put("Topping", "top");
            
            // registry.getCurrentObservation().start().getContext().put("Topping", "top");
            //registry.getCurrentObservation().getContext().put("MethodName", "pizzaDoughprepare");
            TempClass.someValue = "pizzaDoughprepare";
            TempClass.systemTrxId = "A1237";
            Observation.createNotStarted("Topping", registry).observe(() -> doPizzaDoughPrepareElse());
            
           // Observation.createNotStarted("my.operation", () -> context, registry).observe(() -> doSomethingElse());
           //Observation.start("my.operation2", registry).observe(() -> doSomethingElse());
           //registry.getCurrentObservation().getContext().put("MethodName", "pizzaToppingsprepare");
           TempClass.someValue = "pizzaToppingsprepare";
           Observation.createNotStarted("Oven", registry).observe(() -> doPizzaToppingsPrepareElse());

         //  Observation.Scope.
        }
        finally {
            // sample.stop(Timer.builder("my.timer").register(meterRegistry));
        }
        
    //    System.out.println(" Time " + meterRegistry.toString());
    //     list = meterRegistry.getMeters();
        
    //     for (Meter meter : list) {
    //        System.out.println(" Under the list :: " + meter.getId().getName() + "-" + meter.measure().toString());
        
    //     }

        System.out.println("{}         Ended SimpleProgram Main method");
        System.out.println(homePulseObj.outputDom);

       return homePulseObj.outputDom;
    }


    public void doPizzaDoughPrepareElse() {
        System.out.println(">>>>>>>  Started doSomething method");
        MeterRegistry registry = new SimpleMeterRegistry();
        Timer.Sample sample = Timer.start(registry);
        try {
           
        // System.out.println("do Something..");
           Thread.sleep(890L);
        }catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            sample.stop(Timer.builder("my.timer").register(registry));
        }
        System.out.println(">>>>>>>  Ended doSomething method " );
        
    }

    public  void doPizzaToppingsPrepareElse() {
        System.out.println(">>>>>>>  Started doSomethingElse method");
        MeterRegistry registry = new SimpleMeterRegistry();
        Timer.Sample sample = Timer.start(registry);
        try {
            System.out.println("do Something..");
        }
        finally {
            sample.stop(Timer.builder("my.timer").register(registry));
        }
        System.out.println(">>>>>>>  Ended doSomethingElse method " );
    }
       
}
