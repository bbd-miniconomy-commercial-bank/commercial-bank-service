package com.miniconomy.commercial_bank_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniconomy.commercial_bank_service.response.BaseResponse;
import com.miniconomy.commercial_bank_service.service.HealthService;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/")
class HealthController {
    
    final HealthService healthService;

    public HealthController (HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/")
    public BaseResponse getHealthStatus () {
        return this.healthService.retrieveHealthStatus();
    }

}