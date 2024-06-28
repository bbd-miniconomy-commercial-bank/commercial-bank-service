package com.miniconomy.commercial_bank_service.financial_management.service;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.response.BasicResponse;

@Service
public class HealthService {
    
    public BasicResponse<String> retrieveHealthStatus() {
        return new BasicResponse<String>("Commercial Bank Service Available");
    } 

}
