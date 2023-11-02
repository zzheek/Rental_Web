package com.rental_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class RentalWebApplication {

	public static void main(String[] args) {

		SpringApplication.run(RentalWebApplication.class, args);
	}

}
