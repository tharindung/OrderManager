package com.ccw.order_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI orderOpenApi()
    {
        return new OpenAPI()
                .info(new Info()
                        .title("Order Service Microservice APIs")
                        .version("1.0.0")
                        .description("REST API documentation for Order Service Microservice project")
                        .contact(new Contact()
                                .name("Tharindu Nadeesha")));
    }
}