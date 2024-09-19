package com.dungle.social;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dungle.controller","com.dungle.repository","com.dungle.service","com.dungle.config,com.dungle.exceptions"})
@EnableJpaRepositories(basePackages = "com.dungle.repository")
@EntityScan(basePackages = {"com.dungle.models"})
public class SocialApplication {
	public static void main(String[] args) {
		SpringApplication.run(SocialApplication.class, args);
	}
}
