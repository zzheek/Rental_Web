package com.rental_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing	// 엔티티가 DB 추가 or 변경 시 자동으로 시간 값 지정
public class RentalWebApplication  {

	public static void main(String[] args) {

		SpringApplication.run(RentalWebApplication.class, args);
	}

}
