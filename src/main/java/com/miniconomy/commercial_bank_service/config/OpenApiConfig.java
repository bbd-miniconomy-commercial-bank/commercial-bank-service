package com.miniconomy.commercial_bank_service.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Commercial Bank Service API",
        version = "1.0.0",
        description = "API mappings for the Commercial Bank Service"
    )
)
public class OpenApiConfig {
    
}
