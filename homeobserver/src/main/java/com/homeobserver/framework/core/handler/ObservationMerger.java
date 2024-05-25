package com.homeobserver.framework.core.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;

import com.homeobserver.framework.core.app.HomeObserveConfiguration;
import com.homeobserver.framework.core.aspect.Carrier;
import com.homeobserver.framework.core.controller.AnotherSimpleProgram;

import io.micrometer.common.KeyValue;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.ObservationTextPublisher;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Tracer;

@Component
public class ObservationMerger {

    static int i = 0;

    @Autowired
    HomeObserveConfiguration homeObserveConfiguration;

    public Observation mergeObservations(ProceedingJoinPoint jointPoint, Carrier carrier) {
        ThreadLocalBag.setThreadLocalValue(carrier);
        Observation observation = null;
        try {
            observation = Observation.createNotStarted("ObservationMerger : ",
                    homeObserveConfiguration.createObservation());
            observation.contextualName("Balaji - " + (i++));

            System.err.println("ObservationMerger :Merger method " + carrier.getMethodName());

            KeyValue keyValue1 = KeyValue.of("Method Name", carrier.getMethodName());

            observation.getContext().addHighCardinalityKeyValue(keyValue1);

            observation.observe(() -> {
                try {
                    return jointPoint.proceed(); // Invoke the method dynamically
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    return null; // Handle the exception gracefully
                }
            });
        } finally {
            // sample.stop(Timer.builder("my.timer").register(meterRegistry));
        }

        return observation;
    }

    private void doPizzaToppingsPrepareElse() {
        System.out.println("at the dough doPizzaToppingsPrepareElse method..");
    }

    private void doPizzaDoughPrepareElse() {
        System.out.println("at the dough doPizzaDoughPrepareElse method..");
    }

    public void stopObservation(Observation observation) {
        System.err.println("Stopping the observation..");
        observation.stop();

    }

}
