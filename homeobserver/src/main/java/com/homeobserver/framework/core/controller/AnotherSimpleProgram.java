package com.homeobserver.framework.core.controller;


import java.util.List;

import org.springframework.context.annotation.Bean;

import com.homeobserver.framework.core.handler.HomePulseHandler;
import com.homeobserver.framework.core.handler.MyObservationHandler;

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



public class AnotherSimpleProgram {
    

    private List<Meter> list;

 
    /**
     * @return
     */
    public void doObserve(){
        // //System.out.println("Started SimpleProgram Main method");

        MeterRegistry meterRegistry = new SimpleMeterRegistry();

        ObservationRegistry registry = ObservationRegistry.create();      
        // CustomHandler customHandler =  new CustomHandler();
        // registry.observationConfig().observationHandler(customHandler)
        // .observationHandler(new ObservationTextPublisher(System.out::println))
        // .observationHandler(new DefaultMeterObservationHandler(meterRegistry));

        HomePulseHandler myObserverObj = new HomePulseHandler();
        registry.observationConfig().observationHandler(myObserverObj)
        .observationHandler(new ObservationTextPublisher(System.out::println))
        .observationHandler(new DefaultMeterObservationHandler(meterRegistry));

        try {

            Observation.createNotStarted("Topping", registry).observe(() -> doPizzaDoughPrepareElse());
           Observation.createNotStarted("Oven", registry).observe(() -> doPizzaToppingsPrepareElse());

        } finally {
            // sample.stop(Timer.builder("my.timer").register(meterRegistry));
        }

        System.out.println("{}         Ended SimpleProgram Main method");
       

       //return homePulseObj.outputDom;
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

