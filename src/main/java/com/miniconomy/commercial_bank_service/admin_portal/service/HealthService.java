package com.miniconomy.commercial_bank_service.admin_portal.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.admin_portal.response.ResponseTemplate;

@Service
public class HealthService {
    
    public ResponseTemplate<String> retrieveHealthStatus() {
        return new ResponseTemplate<String>(HttpStatus.OK.value(), null, "Commercial Bank Service Available");
    } 

}
