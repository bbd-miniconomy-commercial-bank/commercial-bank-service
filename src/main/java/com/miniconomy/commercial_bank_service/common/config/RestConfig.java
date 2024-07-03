package com.miniconomy.commercial_bank_service.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.ArrayList;

import com.miniconomy.commercial_bank_service.common.interceptor.HeaderRequestInterceptor;

@Configuration
public class RestConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Create a list for interceptors
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

        // Add your custom interceptor
        interceptors.add(new HeaderRequestInterceptor("X-Origin", "commercial_bank"));

        // Set the interceptors to the RestTemplate
        restTemplate.setInterceptors(interceptors);

        return new RestTemplate();
    }
}
