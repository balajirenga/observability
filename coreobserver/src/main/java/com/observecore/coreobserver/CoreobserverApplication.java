package com.observecore.coreobserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.observecore.coreobserver.*")
public class CoreobserverApplication {

	public static void main(String[] args) {
		System.out.println("Core Observer Application Started....");
		SpringApplication.run(CoreobserverApplication.class, args);
	}

}
