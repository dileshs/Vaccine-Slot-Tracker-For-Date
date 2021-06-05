package com.dilesh.VaccineSlotTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.dilesh.VaccineSlotTracker")
@EnableAutoConfiguration
public class VaccineSlotTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaccineSlotTrackerApplication.class, args);
	}

}
