package com.miniconomy.commercial_bank_service.financial_management.builder;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.command.BasicTransactionCommand;
import com.miniconomy.commercial_bank_service.financial_management.command.DebitOrderTransactionCommand;
import com.miniconomy.commercial_bank_service.financial_management.command.IncomingInterbankTransactionCommand;
import com.miniconomy.commercial_bank_service.financial_management.command.LoanTransactionCommand;
import com.miniconomy.commercial_bank_service.financial_management.command.NotifyTransactionCommand;
import com.miniconomy.commercial_bank_service.financial_management.command.OutgoingInterbankTransactionCommand;
import com.miniconomy.commercial_bank_service.financial_management.command.TransactionCommand;
import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrder;
import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrderTransaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.IncomingInterbankDeposit;
import com.miniconomy.commercial_bank_service.financial_management.entity.InterbankTransaction;
import com.miniconomy.commercial_bank_service.financial_management.entity.Loan;
import com.miniconomy.commercial_bank_service.financial_management.entity.Transaction;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.InterbankTransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.LoanTypeEnum;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.NotificationTypeEnum;
import com.miniconomy.commercial_bank_service.financial_management.enumeration.TransactionStatusEnum;
import com.miniconomy.commercial_bank_service.financial_management.service.AccountService;
import com.miniconomy.commercial_bank_service.financial_management.service.DebitOrderService;
import com.miniconomy.commercial_bank_service.financial_management.service.InterbankService;
import com.miniconomy.commercial_bank_service.financial_management.service.LoanService;
import com.miniconomy.commercial_bank_service.financial_management.service.NotificationService;
import com.miniconomy.commercial_bank_service.financial_management.service.TransactionService;
import com.miniconomy.commercial_bank_service.simulation_management.store.SimulationStore;

import java.util.Optional;

@Service
public class TransactionCommandBuilder {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final LoanService loanService;
    private final DebitOrderService debitOrderService;
    private final InterbankService interbankService;
    private final NotificationService notificationService;

    public TransactionCommandBuilder(
        @Lazy AccountService accountService, 
        @Lazy TransactionService transactionService, 
        @Lazy LoanService loanService,
        @Lazy DebitOrderService debitOrderService,
        @Lazy InterbankService interbankService,
        @Lazy NotificationService notificationService
    ) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.loanService = loanService;
        this.debitOrderService = debitOrderService;
        this.interbankService = interbankService;
        this.notificationService = notificationService;
    }

    public TransactionCommand buildTransactionCommand(Transaction transaction, boolean notifyDebitAccount, boolean notifyCreditAccount) {
        TransactionCommand transactionCommand;

        Optional<Account> crAccountOptional = accountService.retrieveAccountByName(transaction.getCreditAccountName());
        if (crAccountOptional.isPresent()) {
            transaction.setTransactionStatus(TransactionStatusEnum.complete);
            transactionCommand = new BasicTransactionCommand(transaction, transactionService, accountService);
        } else {
            String externalAccountId = transaction.getCreditAccountName();
            transaction.setCreditAccountName("retail-bank");
            transactionCommand = new BasicTransactionCommand(transaction, transactionService, accountService);
            transactionCommand = new OutgoingInterbankTransactionCommand(transactionCommand, interbankService, "retail-bank", externalAccountId);
        }

        if (notifyDebitAccount) {
            transactionCommand = new NotifyTransactionCommand(transactionCommand, notificationService, transaction.getDebitAccountName(), NotificationTypeEnum.outgoing_payment);
        }
        if (notifyCreditAccount) {
            transactionCommand = new NotifyTransactionCommand(transactionCommand, notificationService, transaction.getCreditAccountName(), NotificationTypeEnum.incoming_payment);
        }

        return transactionCommand;
    }

    public TransactionCommand buildTransactionCommand(Loan loan) {

        long repaymentAmount = (long) (loan.getLoanType().equals(LoanTypeEnum.short_term) ? loan.getLoanAmount() / 6 * 1.1 : loan.getLoanAmount() / 12 * 1.2);

        Transaction transaction = new Transaction(
            null, 
            loan.getAccountName(), 
            "commerial-bank", 
            "Auto Loan Repayment", 
            "Loan Repayment", 
            repaymentAmount, 
            SimulationStore.getCurrentDate(), 
            TransactionStatusEnum.complete
        );

        TransactionCommand transactionCommand = buildTransactionCommand(transaction, true, false);
        transactionCommand = new LoanTransactionCommand(transactionCommand, loanService, loan.getLoanId());

        return transactionCommand;
    }

    public TransactionCommand buildTransactionCommand(DebitOrder debitOrder) {

        boolean interbankPayment = accountService.retrieveAccountByName(debitOrder.getCreditAccountName()).isPresent();

        Transaction transaction = new Transaction(
            null, 
            debitOrder.getDebitAccountName(), 
            debitOrder.getCreditAccountName(), 
            debitOrder.getDebitOrderDebitRef(), 
            debitOrder.getDebitOrderCreditRef(), 
            debitOrder.getDebitOrderAmount(), 
            SimulationStore.getCurrentDate(), 
            interbankPayment ? TransactionStatusEnum.complete : TransactionStatusEnum.pending
        );

        TransactionCommand transactionCommand = buildTransactionCommand(transaction, true, !interbankPayment);

        DebitOrderTransaction debitOrderTransaction = new DebitOrderTransaction();
        debitOrderTransaction.setDebitOrderId(debitOrder.getDebitOrderId());

        transactionCommand = new DebitOrderTransactionCommand(transactionCommand, debitOrderService, debitOrderTransaction);

        return transactionCommand;
    }

    public TransactionCommand buildTransactionCommand(IncomingInterbankDeposit incomingInterbankDeposit) {

        Transaction transaction = new Transaction(
            null, 
            "retail-bank", 
            incomingInterbankDeposit.getCreditAccountName(), 
            incomingInterbankDeposit.getDebitRef(), 
            incomingInterbankDeposit.getCreditRef(), 
            incomingInterbankDeposit.getAmount(), 
            SimulationStore.getCurrentDate(), 
            TransactionStatusEnum.complete
        );

        TransactionCommand transactionCommand = buildTransactionCommand(transaction, false, true);

        InterbankTransaction interbankTransaction = new InterbankTransaction();
        interbankTransaction.setExternalAccountId(incomingInterbankDeposit.getCreditAccountName());
        interbankTransaction.setInterbankTransactionStatus(InterbankTransactionStatusEnum.complete);

        transactionCommand = new IncomingInterbankTransactionCommand(transactionCommand, interbankService, interbankTransaction);

        return transactionCommand;
    }

}
