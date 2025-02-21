package com.retailer.rewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The main entry point for the Rewards Application.
 * This class initializes and starts the Spring Boot application.
 */
@SpringBootApplication
@EntityScan(basePackages = "com.retailer.rewards.entity")
@EnableJpaRepositories(basePackages = "com.retailer.rewards.repository")
public class RewardsApplication {

	/**
     * The main method that launches the Rewards Application.
     *
     * @param args Command-line arguments passed during application startup.
     */
	public static void main(String[] args) {
		SpringApplication.run(RewardsApplication.class, args);
	}

}
