package com.miniconomy.commercial_bank_service.service;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.response.BaseResponse;

@Service
public class HealthService {
    
    public BaseResponse retrieveHealthStatus() {
        return new BaseResponse("Commercial Bank Service Available");
    } 

}
