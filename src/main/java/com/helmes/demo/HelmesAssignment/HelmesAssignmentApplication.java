package com.helmes.demo.HelmesAssignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HelmesAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelmesAssignmentApplication.class, args);
	}

	@Bean
	@Autowired
	CommandLineRunner commandLineRunner() {
		return args -> {
		};
	}

}
