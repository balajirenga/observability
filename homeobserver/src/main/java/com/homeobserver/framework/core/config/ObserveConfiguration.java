package com.homeobserver.framework.core.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import com.homeobserver.framework.core.aop.AbstractObserveAroundMethodHandler;
import com.homeobserver.framework.core.aop.DefaultObserveAroundMethodHandler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ObserveConfiguration {

    // @Bean
    // @ConditionalOnMissingBean(AbstractObserveAroundMethodHandler.class)
    // AbstractObserveAroundMethodHandler observeAroundMethodHandler() {
    //     return new DefaultObserveAroundMethodHandler();
    // }

    // @Bean
    // @ConditionalOnMissingBean(ObservedAspect.class)
    // ObservedAspect observedAspect(ObservationRegistry observationRegistry) {

    //     		// AbstractObserveAroundMethodHandler		
    //     System.out.println("[Startup] ObserveConfiguration || Creating Observation....");
	// 	//AbstractObserveAroundMethodHandler abstractObserveAroundMethodHandler = new AbstractObserveAroundMethodHandler();
    //     DefaultObserveAroundMethodHandler defaultObserveAroundMethodHandler = new DefaultObserveAroundMethodHandler();
        
    //     observationRegistry.observationConfig().observationHandler(defaultObserveAroundMethodHandler); 
    //     System.out.println("[End] ObserveConfiguration || Creating Observation....setting up observationHandler....");

    //     return new ObservedAspect(observationRegistry);
    // }

    	// @Bean just copy pasted for reference..
	// public MyObservationHandler createHandler() {
	// 	//Carrier carrier = new Carrier();
	// 	ObservationRegistry registry = createObservation();
	// 	MeterRegistry meterRegistry = new SimpleMeterRegistry();

	//     MyObservationHandler myObserverObj = new MyObservationHandler();

    //     registry.observationConfig().observationHandler(myObserverObj)
    //     .observationHandler(new ObservationTextPublisher(System.out::println))
    //     .observationHandler(new DefaultMeterObservationHandler(meterRegistry));
	// 	return myObserverObj;
	// }	

}
