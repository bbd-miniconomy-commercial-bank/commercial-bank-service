package com.miniconomy.commercial_bank_service.financial_management.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.miniconomy.commercial_bank_service.financial_management.entity.OutgoingInterbankDeposit;
import com.miniconomy.commercial_bank_service.financial_management.request.OutgoingRetailBankRequest;
import com.miniconomy.commercial_bank_service.financial_management.utils.InterbankUtils;

@Service
public class RetailBankAdapter implements InterbankAdapter {

    @Value("${externalbank.retailbank.endpoint}")
    private String retailBankEndpoint;

    private final RestTemplate restTemplate;

    public RetailBankAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean processInterbankDeposit(OutgoingInterbankDeposit outgoingInterbankDeposit) {
        OutgoingRetailBankRequest outgoingRetailBankRequest = InterbankUtils.outgoingRetailBankMapper(outgoingInterbankDeposit);
        ResponseEntity<OutgoingRetailBankRequest> response = restTemplate.postForEntity(retailBankEndpoint, outgoingRetailBankRequest, OutgoingRetailBankRequest.class);

        return response.getStatusCode() == HttpStatus.ACCEPTED;
    }
    
}
