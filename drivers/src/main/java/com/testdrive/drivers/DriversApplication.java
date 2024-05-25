package com.testdrive.drivers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.observecore.coreobserver.CoreobserverApplication;
import com.observecore.coreobserver.myaspect.*;

@SpringBootApplication
public class DriversApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriversApplication.class, args);
		//SpringApplication.run(CoreobserverApplication.class, args);
	}

}
