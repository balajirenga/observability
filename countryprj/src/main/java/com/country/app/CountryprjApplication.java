package com.country.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

import com.country.Observation.BasicSpringSetup;
import com.country.Observation.ManualOpenTelemetryTracer;

@SpringBootApplication
public class CountryprjApplication {

	public static void main(String[] args) {
		System.out.println("hello in the application start..");
		//new BasicSpringSetup().setup();
		new ManualOpenTelemetryTracer().setup();
		SpringApplication.run(CountryprjApplication.class, args);
	}

	// @Bean
    // ProtobufHttpMessageConverter protobufHttpMessageConverter() {
    //     return new ProtobufHttpMessageConverter();
    // }
}
