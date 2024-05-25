package com.homeobserver.framework.core.aop;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.aop.ObservedAspect;
import jakarta.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;


// @Component
public class AbstractObserveAroundMethodHandler extends AbstractLogAspect
        implements ObservationHandler<ObservedAspect.ObservedAspectContext> {

    @PostConstruct
    public void init() {
       // System.out.println("AbstractObserveAroundMethodHandler Aspect is initialized..");
    }        

        // private final Tracer tracer;
    // private Carrier carrier;

    public AbstractObserveAroundMethodHandler() {
        // this.tracer = tracer;
        // this.carrier = carrier;
        System.out.println("New AbstractObserveAroundMethodHandler created");
    }

    @Override
    public void onStart(ObservedAspect.ObservedAspectContext context) {
        System.out.println("Onstart.... AbtractObserveAroundMethodHandler");

        ProceedingJoinPoint joinPoint = context.getProceedingJoinPoint();
        super.logBefore(joinPoint);
    }

    @Override
    public void onStop(ObservedAspect.ObservedAspectContext context) {
        ProceedingJoinPoint joinPoint = context.getProceedingJoinPoint();
        System.out.println("OnEnd.... AbtractObserveAroundMethodHandler");
        super.logAfter(joinPoint);
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return context instanceof ObservedAspect.ObservedAspectContext;
    }
}
