package com.ks.SampleSpringProjectApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SampleSpringProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleSpringProjectApplication.class, args);
		System.out.println("Welcome to the World of Srushti's Spring Boot Application...!");
	}
}
