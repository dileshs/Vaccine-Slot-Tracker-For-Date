package com.dilesh.VaccineSlotTracker.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.client.RestTemplate;

import com.dilesh.VaccineSlotTracker.service.VaccineTrackerImpl;

@Configuration
public class InitializerConfig implements CommandLineRunner, ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private VaccineTrackerImpl vaccineTrackerImpl;

	private String[] args;

	/*@Bean
	public static PropertySourcesPlaceholderConfigurer createPropertyConfigurer() {
		PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertyConfigurer.setLocation(new FileSystemResource("C:/vaccine resource/app.properties"));
		propertyConfigurer.setTrimValues(true);
		return propertyConfigurer;
	}*/

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		try {
			vaccineTrackerImpl.trackerOrchestrator(this.args);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run(String... args) throws Exception {

		this.args = args;

	}

}
