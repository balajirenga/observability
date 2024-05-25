package com.demopackaging.packagingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication(scanBasePackages = {"com.demopackaging.packagingdemo"})
public class PackagingdemoApplication {


	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PackagingdemoApplication.class, args);
		KafkaProducer kafkaProducer = context.getBean(KafkaProducer.class);
		System.out.println("About to post the message.");
		kafkaProducer.sendMessage("helloworld", "Onstart message from Packaging Service");
		System.out.println("Posted the message on start.."); 
	}

}
