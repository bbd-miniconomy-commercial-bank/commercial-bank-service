package com.miniconomy.commercial_bank_service.financial_management.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.request.NotificationRequest;

import java.util.Optional;

@Service
public class NotificationService {

    private final RestTemplate restTemplate;

    private final AccountService accountService;

    public NotificationService(RestTemplate restTemplate, AccountService accountService) {
        this.restTemplate = restTemplate;
        this.accountService = accountService;
    }

    public void sendTransactionNotification(NotificationRequest notificationRequest, String accountName) {
        
        Optional<Account> accountOptional = accountService.retrieveAccountByName(accountName);
        
        if (accountOptional.isPresent()) {
            String url = accountOptional.get().getAccountNotificationEndpoint();
            if (!url.isBlank()) {
                restTemplate.postForEntity(url, notificationRequest, NotificationRequest.class);
            }
        }
    }
}
