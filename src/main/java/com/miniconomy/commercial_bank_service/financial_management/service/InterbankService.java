package com.miniconomy.commercial_bank_service.financial_management.service;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankTransaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.OutgoingInterbankDeposit;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.invoker.TransactionInvoker;
import com.miniconomy.commercial_bank_service.financial_management.builder.TransactionCommandBuilder;
import com.miniconomy.commercial_bank_service.financial_management.command.TransactionCommand;
import com.miniconomy.commercial_bank_service.financial_management.entity.IncomingInterbankDeposit;
import com.miniconomy.commercial_bank_service.financial_management.entity.IncomingInterbankDepositCallback;
import com.miniconomy.commercial_bank_service.financial_management.port.InterbankPort;
import com.miniconomy.commercial_bank_service.financial_management.repository.InterbankTransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InterbankService {
    
    private final InterbankTransactionRepository interbankTransactionRepository;
    private final InterbankPort interbankPort;

    private final TransactionCommandBuilder transactionCommandBuilder;

    public InterbankService(InterbankTransactionRepository interbankTransactionRepository, InterbankPort interbankPort, TransactionCommandBuilder transactionCommandBuilder) {
        this.interbankTransactionRepository = interbankTransactionRepository;
        this.interbankPort = interbankPort;
        this.transactionCommandBuilder = transactionCommandBuilder;
    }

    public List<InterbankTransaction> processDeposits(List<IncomingInterbankDeposit> incomingInterbankDeposits, String accountName) {
        List<InterbankTransaction> interbankTransactions = new ArrayList<>();
        
        for (IncomingInterbankDeposit incomingInterbankDeposit : incomingInterbankDeposits) {
            TransactionCommand transactionCommand = transactionCommandBuilder.buildTransactionCommand(incomingInterbankDeposit);
            Transaction transaction = TransactionInvoker.handler(transactionCommand);

            if (transaction.getTransactionStatus().equals(TransactionStatusEnum.failed)) {
                // PERFORM RESOLUTION
            }

            if (transaction.getTransactionId() != null) {
                Optional<InterbankTransaction> interbankTransactionOptional = interbankTransactionRepository.getByTransactionId(transaction.getTransactionId());

                if (interbankTransactionOptional.isPresent()) {
                    InterbankTransaction interbankTransaction = interbankTransactionOptional.get();
                    interbankTransactions.add(interbankTransaction);
                }
            }
        }

        return interbankTransactions;
    }

    public Optional<InterbankTransaction> saveInterbankTransaction(InterbankTransaction interbankTransaction) {
        return interbankTransactionRepository.insert(interbankTransaction);
    }

    public Optional<InterbankTransaction> updateInterbankTransaction(InterbankTransaction interbankTransaction) {
        return interbankTransactionRepository.update(interbankTransaction);
    }

    public void processDepositCallback(IncomingInterbankDepositCallback incomingInterbankDepositCallback) {
        boolean processed = interbankPort.sendIncomingDepositCallback(incomingInterbankDepositCallback);

        if (!processed) {
            //ADD TO RETRY QUEUE
        }
    }

    public void sendOutgoingDeposit(OutgoingInterbankDeposit outgoingInterbankDeposit) {
        boolean processed = interbankPort.sendOutgoingDeposit(outgoingInterbankDeposit);

        if (!processed) {
            //ADD TO RETRY QUEUE
        }
    }

}
