package com.miniconomy.commercial_bank_service.service;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.response.BasicResponse;

@Service
public class HealthService {
    
    public BasicResponse retrieveHealthStatus() {
        return new BasicResponse("Commercial Bank Service Available");
    } 

}
