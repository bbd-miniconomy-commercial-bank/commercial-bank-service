package com.miniconomy.commercial_bank_service.financial_management.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.miniconomy.commercial_bank_service.financial_management.request.NotificationRequest;

@Service
public class NotificationService {

    private final RestTemplate restTemplate;

    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendTransactionNotification(NotificationRequest notificationRequest, String accountName) {
        String url = "localhost:5500"; // TEMP
        restTemplate.postForEntity(url, notificationRequest, NotificationRequest.class);
    }
}
