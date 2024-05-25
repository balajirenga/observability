package com.homeobserver.framework.core.config;

import com.homeobserver.framework.core.aop.AbstractLogAspect;
import com.homeobserver.framework.core.aop.DefaultLogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LogAspectConfig {

    // @Bean
    // @ConditionalOnMissingBean
    // AbstractLogAspect defaultLogAspect() {
    //     System.err.println("[Startup] LogAspectConfig || Creating DefaultLogAspect....");
    //     return new DefaultLogAspect();
    // }

}
