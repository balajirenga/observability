package com.demooven.ovendemo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({com.observecore.coreobserver.CoreobserverApplication.class})
public class AppConfig {
    
}