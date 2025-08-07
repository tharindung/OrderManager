package com.ccw.staff_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI staffOpenApi()
    {
        return new OpenAPI()
                .info(new Info()
                        .title("Staff Service Microservice APIs")
                        .version("1.0.0")
                        .description("REST API documentation for Staff Service Microservice project")
                        .contact(new Contact()
                                .name("Tharindu Nadeesha")));
    }
}



