package com.miniconomy.commercial_bank_service.financial_management.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.miniconomy.commercial_bank_service.financial_management.entity.OutgoingInterbankDeposit;
import com.miniconomy.commercial_bank_service.financial_management.request.OutgoingRetailBankRequest;
import com.miniconomy.commercial_bank_service.financial_management.utils.InterbankUtils;

@Service
public class RetailBankAdapter implements InterbankAdapter {

    private final RestTemplate restTemplate;

    public RetailBankAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean processInterbankDeposit(OutgoingInterbankDeposit outgoingInterbankDeposit) {
        String url = "localhost:5500"; // TEMP
        OutgoingRetailBankRequest outgoingRetailBankRequest = InterbankUtils.outgoingRetailBankMapper(outgoingInterbankDeposit);
        ResponseEntity<OutgoingRetailBankRequest> response = restTemplate.postForEntity(url, outgoingRetailBankRequest, OutgoingRetailBankRequest.class);

        return response.getStatusCode() == HttpStatus.ACCEPTED;
    }
    
}
