package com.miniconomy.commercial_bank_service.financial_management.service;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankTransaction;
import com.miniconomy.commercial_bank_service.financial_management.request.InterbankDepositRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class InterbankService {
    
    public List<InterbankTransaction> processDeposits(List<InterbankDepositRequest> depositsRequests, String accountName) {
        return new ArrayList<>();
    }
}
