package com.miniconomy.commercial_bank_service.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.financial_management.response.ResponseTemplate;
import com.miniconomy.commercial_bank_service.financial_management.service.HealthService;

import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;

@Hidden
@RestController
@RequestMapping("/")
class HealthController {
    
    final HealthService healthService;

    public HealthController (HealthService healthService) {
        this.healthService = healthService;
    }
    
    @GetMapping(value = "/", produces = "application/json")
    public ResponseTemplate<Map<String, String>> getHealthStatus (HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }

        return new ResponseTemplate<>(200, headers, null);
        //return this.healthService.retrieveHealthStatus();
    }

}