package com.uken.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.uken.api.rest")
public class MotovaultRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotovaultRestApplication.class, args);
	}


}
