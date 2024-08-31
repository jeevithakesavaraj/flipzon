package com.ideas2it.flipzon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FlipzonApplication {

	public static void main(String[] args) {

		SpringApplication.run(FlipzonApplication.class, args);
	}

}
