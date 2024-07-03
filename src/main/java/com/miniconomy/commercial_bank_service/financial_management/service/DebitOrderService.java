package com.miniconomy.commercial_bank_service.financial_management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.builder.TransactionCommandBuilder;
import com.miniconomy.commercial_bank_service.financial_management.command.TransactionCommand;
import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrder;
import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrderTransaction;
import com.miniconomy.commercial_bank_service.financial_management.invoker.TransactionInvoker;
import com.miniconomy.commercial_bank_service.financial_management.repository.AccountRepository;
import com.miniconomy.commercial_bank_service.financial_management.repository.DebitOrderRepository;
import com.miniconomy.commercial_bank_service.financial_management.repository.DebitOrderTransactionRepository;
import com.miniconomy.commercial_bank_service.simulation_management.store.SimulationStore;
@Service
public class DebitOrderService {

    private final DebitOrderRepository debitOrderRepository;
    private final DebitOrderTransactionRepository debitOrderTransactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionCommandBuilder transactionCommandBuilder;

    public DebitOrderService(
        DebitOrderRepository debitOrderRepository, 
        DebitOrderTransactionRepository debitOrderTransactionRepository, 
        AccountRepository accountRepository,
        TransactionCommandBuilder transactionCommandBuilder
    ) {
        this.debitOrderRepository = debitOrderRepository;
        this.debitOrderTransactionRepository = debitOrderTransactionRepository;
        this.accountRepository = accountRepository;
        this.transactionCommandBuilder = transactionCommandBuilder;
    }

    public boolean disableDebitOrder(UUID debitOrderId, String accountName) {
        Optional<DebitOrder> debitOrderOptional = debitOrderRepository.findById(debitOrderId, accountName);
        if (debitOrderOptional.isPresent()) {
            DebitOrder debitOrder = debitOrderOptional.get();
            debitOrder.setDebitOrderDisabled(true);
            debitOrderRepository.update(debitOrder);
            return true;
        }
        return false;
    }

    public Optional<DebitOrder> updateDebitOrder(UUID debitOrderId, DebitOrder dbOrder, String accountName) {
        
        Optional<DebitOrder> dbo = Optional.empty();

        if (dbOrder.getDebitOrderId().equals(debitOrderId)) {
            dbo = debitOrderRepository.findById(debitOrderId, accountName);
            Optional<Account> credAcc = accountRepository.findByAccountName(dbOrder.getCreditAccountName());
            Optional<Account> debAcc = accountRepository.findByAccountName(dbOrder.getDebitAccountName());

            if (dbo.isPresent() && credAcc.isPresent() && debAcc.isPresent()) {
                dbo = debitOrderRepository.update(dbOrder);
            }
        }
        
        return dbo;
    }

    public Optional<DebitOrder> getDebitOrderById(UUID debitOrderId, String accountName) {
        return debitOrderRepository.findById(debitOrderId, accountName);
    }

    public List<DebitOrder> retrieveDebitOrders(String accountName, Pageable pageable) {
        return debitOrderRepository.findAllByCreditAccount(accountName, pageable);
    }

    public List<DebitOrder> saveDebitOrders(List<DebitOrder> dbOrders, String requestingAccountName) {
        List<DebitOrder> debitOrders = new ArrayList<>();

        for (DebitOrder dbOrder : dbOrders) {

            Optional<Account> dbAcc = accountRepository.findByAccountName(dbOrder.getDebitAccountName());
            Optional<Account> crAcc = accountRepository.findByAccountName(dbOrder.getCreditAccountName());

            if (dbAcc.isPresent() && crAcc.isPresent()) {
                
                dbOrder.setDebitOrderCreatedDate(SimulationStore.getCurrentDate());
                Optional<DebitOrder> savedDbo = debitOrderRepository.insert(dbOrder);

                if (savedDbo.isPresent()) {
                    debitOrders.add(savedDbo.get());
                }
            }
        }

        return debitOrders;
    }

    public void processDebitOrders() {

        int debitOrderPerBatch = 25;
        int page = 1;

        Pageable pageable = PageRequest.of(page, debitOrderPerBatch);
        List<DebitOrder> debitOrders = debitOrderRepository.findAll(pageable);
        while (debitOrders.size() > 0) {

            for (DebitOrder debitOrder : debitOrders) {
                TransactionCommand transactionCommand = transactionCommandBuilder.buildTransactionCommand(debitOrder);
                TransactionInvoker.handler(transactionCommand);
            }

            pageable = PageRequest.of(++page, debitOrderPerBatch);
            debitOrders = debitOrderRepository.findAll(pageable);
        }

    }

    public Optional<DebitOrderTransaction> saveDebitOrderTransaction(DebitOrderTransaction debitOrderTransaction) {
        return debitOrderTransactionRepository.insert(debitOrderTransaction);
    }

}
