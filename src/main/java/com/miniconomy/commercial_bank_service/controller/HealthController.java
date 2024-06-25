package com.miniconomy.commercial_bank_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.response.BasicResponse;
import com.miniconomy.commercial_bank_service.service.HealthService;

import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Health Status", description = "Queries related to service's health status")
@RestController
@RequestMapping("/")
class HealthController {
    
    final HealthService healthService;

    public HealthController (HealthService healthService) {
        this.healthService = healthService;
    }
    
    @Operation(
        summary = "Get service's health status",
        description = "Allows other services to check the service's current health status"
    )
    @GetMapping(value = "/", produces = "application/json")
    public BasicResponse<String> getHealthStatus () {
        return this.healthService.retrieveHealthStatus();
    }

}