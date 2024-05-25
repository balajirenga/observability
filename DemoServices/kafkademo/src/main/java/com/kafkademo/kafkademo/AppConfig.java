package com.kafkademo.kafkademo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({com.observecore.coreobserver.CoreobserverApplication.class})
public class AppConfig {
    public static final String HELLOWORLD_TOPIC = "helloworld";
}
