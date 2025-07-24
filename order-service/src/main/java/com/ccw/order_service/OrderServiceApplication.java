package com.ccw.order_service;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {

	/* Configure RestTemplate as a Spring Bean */
	/*@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}*/

	/* Configure WebClient as a Spring Bean */
	@Bean
	public WebClient webClient()
	{
		return WebClient.builder().build();
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
