package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankTransaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.OutgoingInterbankDeposit;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.InterbankTransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.service.InterbankService;

import java.util.Optional;

public class OutgoingInterbankTransactionCommand extends TransactionCommandDecorator {
    
    private InterbankService interbankService;
    
    private String externalBankId;
    private String creditAccountName;

    public OutgoingInterbankTransactionCommand(TransactionCommand transactionCommand, InterbankService interbankService, String externalBankId, String creditAccountName) {
        super(transactionCommand);
        this.interbankService = interbankService;
        this.externalBankId = externalBankId;
        this.creditAccountName = creditAccountName;
    }

    @Override
    public Transaction execute() {
        Transaction transaction = this.transactionCommand.execute();
        
        if (!transaction.getTransactionStatus().equals(TransactionStatusEnum.failed)) {
            // Add to Interbank Transaction Table and deposit to Retail Bank
            InterbankTransaction interbankTransaction = new InterbankTransaction();
            interbankTransaction.setInterbankTransactionId(transaction.getTransactionId());
            interbankTransaction.setInterbankTransactionStatus(InterbankTransactionStatusEnum.pending);

            Optional<InterbankTransaction> interbankTransactionOptional = interbankService.saveInterbankTransaction(interbankTransaction);

            if (interbankTransactionOptional.isPresent()) {
                OutgoingInterbankDeposit outgoingInterbankDeposit = new OutgoingInterbankDeposit(
                    externalBankId,
                    transaction.getDebitAccountName(), 
                    creditAccountName, 
                    transaction.getTransactionAmount(),
                    interbankTransaction.getInterbankTransactionId().toString()
                );

                interbankService.sendOutgoingDeposit(outgoingInterbankDeposit);
            } else {
                // ROLLBACK
            }
        }

        transaction.setCreditAccountName(creditAccountName);
        return transaction;
    }
}
