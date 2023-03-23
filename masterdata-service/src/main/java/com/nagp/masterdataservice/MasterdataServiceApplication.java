package com.nagp.masterdataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MasterdataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasterdataServiceApplication.class, args);
	}

}
