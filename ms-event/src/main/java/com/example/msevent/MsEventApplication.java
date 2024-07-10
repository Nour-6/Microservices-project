package com.example.msevent;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class MsEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEventApplication.class, args);
	}


	@Bean
	public ObjectMapper objectMapper(){
		return new ObjectMapper();
	}
}
