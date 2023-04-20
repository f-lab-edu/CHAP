package com.wook.chap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ChapApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChapApplication.class, args);
	}



}
