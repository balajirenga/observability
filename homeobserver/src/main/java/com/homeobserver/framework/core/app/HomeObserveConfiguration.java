package com.homeobserver.framework.core.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.homeobserver.framework.core.aop.AbstractObserveAroundMethodHandler;
import com.homeobserver.framework.core.aspect.Carrier;
import com.homeobserver.framework.core.handler.BalajiHandler;
import com.homeobserver.framework.core.handler.MyObservationHandler;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.ObservationTextPublisher;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.TracerProvider;
import io.opentelemetry.context.propagation.TextMapPropagator;


@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.homeobserver.framework.*")
public class HomeObserveConfiguration {
		
	@Bean
	public BaseConfig getConfig()
	{
		return new BaseConfig();
	}

	// @Bean
	// public ObservationRegistry createObservation(){
	// 	System.out.println("[Startup] Creating Observation....");
	// 	ObservationRegistry registry = ObservationRegistry.create();  
		 
	// 	return registry;
	// }

	// @Bean
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

	@Bean
	public ObservationRegistry createObservation() {
		//Carrier carrier = new Carrier();
		ObservationRegistry registry = ObservationRegistry.create();  
		MeterRegistry meterRegistry = new SimpleMeterRegistry();

	    BalajiHandler myObserverObj = new BalajiHandler();

        registry.observationConfig().observationHandler(myObserverObj)
        .observationHandler(new ObservationTextPublisher(System.out::println))
        .observationHandler(new DefaultMeterObservationHandler(meterRegistry));

		System.out.println("Registry set with Balaji Handler");
		return registry;
	}	

	@Bean
    public Tracer tracer() {
		System.out.println("Tracer created");
        // Assuming you have already configured OpenTelemetry with a TracerProvider
        TracerProvider tracerProvider = GlobalOpenTelemetry.getTracerProvider();
		Tracer tracer = tracerProvider.get("your-instrumentation-name", "1.0.0");
         
		System.out.println("Tracer created  " + tracer.toString());

		return tracer;
    }

	// @Bean
    // public TextMapPropagator customTextMapPropagator() {
    //     // Create a custom TextMapPropagator that includes propagators for custom headers
    //     TextMapPropagator textMapPropagator = TextMapPropagator.composite(
    //         // Add propagators for standard headers (e.g., B3 propagation)
    //         TextMapPropagators.createB3Propagator(), // Assuming B3 propagation is used
    //         // Add a propagator for custom headers
    //         new CustomTextMapPropagator()
    //     );
    //     return textMapPropagator;
    // }
}
