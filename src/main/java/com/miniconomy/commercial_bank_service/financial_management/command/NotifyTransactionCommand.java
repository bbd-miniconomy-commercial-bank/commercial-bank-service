package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.NotificationType;
import com.miniconomy.commercial_bank_service.financial_management.request.NotificationRequest;
import com.miniconomy.commercial_bank_service.financial_management.service.NotificationService;
import com.miniconomy.commercial_bank_service.financial_management.utils.TransactionUtils;

public class NotifyTransactionCommand extends TransactionCommandDecorator {

    private NotificationService notificationService;
    private String accountName;

    public NotifyTransactionCommand(Transaction transaction, TransactionCommand transactionCommand, NotificationService notificationService, String accountName) {
        super(transaction, transactionCommand);
        this.notificationService = notificationService;
        this.accountName = accountName;
    }

    @Override
    public void execute() {
        this.transactionCommand.execute();

        NotificationType notificationType = NotificationType.incoming_payment; // IMPLEMENT

        NotificationRequest notificationRequest = new NotificationRequest(
            notificationType, 
            TransactionUtils.transactionResponseMapper(transaction, accountName)
        );

        this.notificationService.sendTransactionNotification(notificationRequest, accountName);
    }
    
}
