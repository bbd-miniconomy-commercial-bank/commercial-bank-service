package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.NotificationTypeEnum;
import com.miniconomy.commercial_bank_service.financial_management.request.NotificationRequest;
import com.miniconomy.commercial_bank_service.financial_management.service.NotificationService;
import com.miniconomy.commercial_bank_service.financial_management.utils.TransactionUtils;

public class NotifyTransactionCommand extends TransactionCommandDecorator {

    private NotificationService notificationService;
    private String accountName;

    public NotifyTransactionCommand(TransactionCommand transactionCommand, NotificationService notificationService, String accountName) {
        super(transactionCommand);
        this.notificationService = notificationService;
        this.accountName = accountName;
    }

    @Override
    public Transaction execute() {
        Transaction transaction = this.transactionCommand.execute();

        NotificationTypeEnum notificationType = NotificationTypeEnum.incoming_payment; // IMPLEMENT

        NotificationRequest notificationRequest = new NotificationRequest(
            notificationType, 
            TransactionUtils.transactionResponseMapper(transaction, accountName)
        );

        this.notificationService.sendTransactionNotification(notificationRequest, accountName);

        return transaction;
    }
    
}
