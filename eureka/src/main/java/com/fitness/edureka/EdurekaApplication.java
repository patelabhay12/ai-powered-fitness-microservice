package com.fitness.edureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EdurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdurekaApplication.class, args);
	}

}
