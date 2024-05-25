package com.demotopping.toppingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(scanBasePackages = {"com.demotopping.toppingdemo"})
@EnableKafka
public class ToppingdemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ToppingdemoApplication.class, args);
		
		KafkaProducer kafkaProducer = context.getBean(KafkaProducer.class);
		System.out.println("About to post the message.");
		kafkaProducer.sendMessage(AppConfig.HELLOWORLD_TOPIC, "Onstart message from Topping Service");
		System.out.println("Posted the message on start.."); 
	} 

}
