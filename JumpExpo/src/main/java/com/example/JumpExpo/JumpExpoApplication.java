package com.example.JumpExpo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JumpExpoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JumpExpoApplication.class, args);
	}

}
