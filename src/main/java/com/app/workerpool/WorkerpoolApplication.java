package com.app.workerpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WorkerpoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkerpoolApplication.class, args);
	}

}
