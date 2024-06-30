package com.miniconomy.commercial_bank_service.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.financial_management.response.ResponseTemplate;
import com.miniconomy.commercial_bank_service.financial_management.service.HealthService;

import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestController
@RequestMapping("/")
class HealthController {
    
    final HealthService healthService;

    public HealthController (HealthService healthService) {
        this.healthService = healthService;
    }
    
    @GetMapping(value = "/", produces = "application/json")
    public ResponseTemplate<String> getHealthStatus () {
        return this.healthService.retrieveHealthStatus();
    }

}