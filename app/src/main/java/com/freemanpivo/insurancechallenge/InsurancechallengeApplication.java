package com.freemanpivo.insurancechallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.freemanpivo.insurancechallenge")
public class InsurancechallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsurancechallengeApplication.class, args);
	}

}
