package com.kumar.find_product.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI openAPIConfig() {
        return new OpenAPI().info(
            new Info()
            .title("Find Product API Documentation")
            .summary("API Specification for APIs in Find Product")
            .description("Contains specification for APIs in 3 specific modules." + 
            "They are shop, product and category")
            .version("1.0")
            .contact(
                new Contact().name("Kumar M")
                .email("kumaranmk33@gmail.com")
            )
        ).addServersItem(
            new Server()
            .description("Local server")
            .url("http://localhost:8080")
        );
    }
}
