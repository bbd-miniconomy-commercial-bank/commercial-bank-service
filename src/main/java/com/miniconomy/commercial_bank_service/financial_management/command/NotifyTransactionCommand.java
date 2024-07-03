package com.miniconomy.commercial_bank_service.financial_management.command;

import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.NotificationTypeEnum;
import com.miniconomy.commercial_bank_service.financial_management.request.NotificationRequest;
import com.miniconomy.commercial_bank_service.financial_management.service.NotificationService;
import com.miniconomy.commercial_bank_service.financial_management.utils.TransactionUtils;

public class NotifyTransactionCommand extends TransactionCommandDecorator {

    private NotificationService notificationService;

    private String accountName;
    private NotificationRequest notificationRequest;

    public NotifyTransactionCommand(TransactionCommand transactionCommand, NotificationService notificationService, String accountName, NotificationTypeEnum notificationType) {
        super(transactionCommand);
        this.notificationService = notificationService;
        this.accountName = accountName;
        this.notificationRequest = new NotificationRequest(notificationType, null);
    }

    @Override
    public Transaction execute() {
        Transaction transaction = this.transactionCommand.execute();

        notificationRequest.setTransaction(TransactionUtils.transactionResponseMapper(transaction, accountName));
        this.notificationService.sendTransactionNotification(notificationRequest, accountName);

        return transaction;
    }

    @Override
    public Transaction rollback() {
        notificationRequest.setType(NotificationTypeEnum.transaction_failed);
        this.notificationService.sendTransactionNotification(notificationRequest, accountName);
        
        return rollback();
    }
    
}
