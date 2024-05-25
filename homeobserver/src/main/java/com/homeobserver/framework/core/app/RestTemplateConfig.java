package com.homeobserver.framework.core.app;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

import com.homeobserver.framework.core.myaspect.OpenTelemetryInterceptor;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.homeobserver.framework.*")
public class RestTemplateConfig {
    
    @Bean
    public OpenTelemetryInterceptor getOpenTelemetryInterceptor() {
        return new OpenTelemetryInterceptor(restTemplateBuilder());
    }

    // @Bean
    // public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    //     System.out.println("@ the Configuration of the RestTemplate");
    //     RestTemplate restTemplate = new RestTemplate();
    //     restTemplate.setInterceptors(Collections.singletonList(openTelemetryInterceptor));
    //     return restTemplate;
    // }

   // private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        System.out.println("@ the Configuration of the RestTemplate");

        RestTemplate restTemplate = new RestTemplate();
        System.out.println("openTelemetryInterceptor  is " + getOpenTelemetryInterceptor());
        restTemplate.setInterceptors(Collections.singletonList(getOpenTelemetryInterceptor()));
        return restTemplate;
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }
}
