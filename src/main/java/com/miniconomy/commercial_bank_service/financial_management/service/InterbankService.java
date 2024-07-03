package com.miniconomy.commercial_bank_service.financial_management.service;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankTransaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.OutgoingInterbankDeposit;
import com.miniconomy.commercial_bank_service.financial_management.port.InterbankPort;
import com.miniconomy.commercial_bank_service.financial_management.repository.InterbankTransactionRepository;
import com.miniconomy.commercial_bank_service.financial_management.request.InterbankDepositRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InterbankService {
    
    private final InterbankTransactionRepository interbankTransactionRepository;
    private final InterbankPort interbankPort;

    public InterbankService(InterbankTransactionRepository interbankTransactionRepository, InterbankPort interbankPort) {
        this.interbankTransactionRepository = interbankTransactionRepository;
        this.interbankPort = interbankPort;
    }

    public List<InterbankTransaction> processDeposits(List<InterbankDepositRequest> depositsRequests, String accountName) {
        return new ArrayList<>();
    }

    public Optional<InterbankTransaction> saveInterbankTransaction(InterbankTransaction interbankTransaction) {
        return interbankTransactionRepository.insert(interbankTransaction);
    }

    public boolean sendOutgoingDeposit(OutgoingInterbankDeposit outgoingInterbankDeposit) {
        return interbankPort.sendOutgoingDeposit(outgoingInterbankDeposit);
    }

}
