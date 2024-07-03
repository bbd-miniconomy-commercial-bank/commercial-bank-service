package com.miniconomy.commercial_bank_service.financial_management.adapter;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.miniconomy.commercial_bank_service.financial_management.entity.OutgoingInterbankDeposit;
import com.miniconomy.commercial_bank_service.financial_management.entity.IncomingInterbankDepositCallback;
import com.miniconomy.commercial_bank_service.financial_management.request.OutgoingRetailBankRequest;
import com.miniconomy.commercial_bank_service.financial_management.request.RetailBankCallbackRequest;
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
        String url = retailBankEndpoint + "/api/transactions/deposits";
        System.out.println(url);
        OutgoingRetailBankRequest outgoingRetailBankRequest = InterbankUtils.outgoingRetailBankRequestMapper(outgoingInterbankDeposit);

        try {
            ResponseEntity<OutgoingRetailBankRequest> response = restTemplate.postForEntity(url, outgoingRetailBankRequest, OutgoingRetailBankRequest.class);
            return response.getStatusCode() == HttpStatus.ACCEPTED;
        } catch (Exception ex) {
            return false;
        }

    }

    public boolean processInterbankDepositCallback(IncomingInterbankDepositCallback incomingInterbankDepositCallback) {
        RetailBankCallbackRequest retailBankCallbackRequest = InterbankUtils.outgoingCallbackRetailBankRequestMapper(incomingInterbankDepositCallback);
        ResponseEntity<OutgoingRetailBankRequest> response = restTemplate.postForEntity(retailBankEndpoint + "api/transactions/" + incomingInterbankDepositCallback.getReference() + "/status", retailBankCallbackRequest, OutgoingRetailBankRequest.class);

        return response.getStatusCode() == HttpStatus.ACCEPTED;
    }
    
}
