package com.bbc.zuber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class DriverAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriverAppApplication.class, args);
	}

	// TODO zestawienie zarobkow, platnosci
	// dodanie auta
	// biblio do info o aucie <optional> https://api-ninjas.com/api/cars
	// liquibase testowe dane
}
