package com.homeobserver.framework.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.homeobserver.framework.core.handler.ObservationMerger;

import io.micrometer.observation.Observation;
import jakarta.annotation.PostConstruct;


@Aspect
@Component
public class EnableHomeObserverAspect {
    
    // after intiialize call the method
    @PostConstruct
    public void init() {
        System.out.println("HomeObserver Aspect is initialized..");
    }

    @Autowired
    CarrierSettingValues carrierSettingValues;

    @Autowired
    ObservationMerger observationMerger;

    @Around("@annotation(homeObserver)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, EnableHomeObserver homeObserver) throws Throwable {

        //System.out.println("EnableHomeObserver - On the aspect call..");
        Carrier carrier = new Carrier();
        carrier.setContextName(homeObserver.contextName());

        Object proceed = null;
        try {


            carrierSettingValues.beforeAnyMethod(joinPoint, carrier);
            carrierSettingValues.myGetAnnotationDetails(joinPoint, carrier);
            System.out.println("EnableHomeObserverAspect: Observation for the method : ===>" + carrier.getMethodName());


            Observation observObj = observationMerger.mergeObservations(joinPoint, carrier);
            

            proceed = joinPoint.proceed();


            // Modernize Technology

           // carrierSettingValues.myGetAnnotationDetails(joinPoint, carrier);
            carrierSettingValues.afterAnyMethod(joinPoint, carrier);
            observationMerger.stopObservation(observObj);

        } catch (Exception e) {
            carrier.setExceptionDetails(e.getMessage());
            System.out.println("Exception occured : " + e.getMessage());
        }
        
        return proceed;
    }

}
