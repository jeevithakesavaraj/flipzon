package com.ideas2it.flipzon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FlipzonApplication {
    private static final Logger logger = LogManager.getLogger(FlipzonApplication.class);
	public static void main(String[] args) {

		SpringApplication.run(FlipzonApplication.class, args);
		logger.info("Application Started");
	}

}
