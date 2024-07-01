package com.miniconomy.commercial_bank_service.financial_management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrder;
import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrderTransaction;
import com.miniconomy.commercial_bank_service.financial_management.repository.AccountRepository;
import com.miniconomy.commercial_bank_service.financial_management.repository.DebitOrderRepository;
import com.miniconomy.commercial_bank_service.financial_management.request.DebitOrderRequest;
import com.miniconomy.commercial_bank_service.financial_management.response.DebitOrderResponse;

@Service
public class DebitOrderService {

    private final DebitOrderRepository debitOrderRepository;
    private final AccountRepository accountRepository;

    public DebitOrderService(DebitOrderRepository debitOrderRepository, AccountRepository accountRepository) {
        this.debitOrderRepository = debitOrderRepository;
        this.accountRepository = accountRepository;
    }

    public boolean disableDebitOrder(UUID debitOrderId) {
        Optional<DebitOrder> debitOrderOptional = debitOrderRepository.findById(debitOrderId);
        if (debitOrderOptional.isPresent()) {
            DebitOrder debitOrder = debitOrderOptional.get();
            debitOrder.setDebitOrderDisabled(true);
            debitOrderRepository.update(debitOrder);
            return true;
        }
        return false;
    }

    public Optional<DebitOrder> updateDebitOrder(UUID id, DebitOrderRequest dbOrder) {
        Optional<DebitOrder> dbo = debitOrderRepository.findById(id);
        Optional<Account> credAcc = accountRepository.findByAccountName(dbOrder.getCreditAccountName());
        Optional<Account> debAcc = accountRepository.findByAccountName(dbOrder.getDebitAccountName());

        if (dbo.isPresent() && credAcc.isPresent() && debAcc.isPresent()) {
            DebitOrder d = dbo.get();
            d.setCreditAccountId(credAcc.get().getAccountId());
            d.setDebitAccountId(debAcc.get().getAccountId());
            d.setDebitOrderAmount(dbOrder.getAmount());
            d.setDebitOrderCreatedDate("Date from Hand of Zeus");
            d.setDebitOrderReceiverRef(dbOrder.getCreditRef());
            d.setDebitOrderSenderRef(dbOrder.getDebitRef());
            debitOrderRepository.update(d);
            return Optional.of(d);
        }
        return Optional.empty();
    }

    public Optional<DebitOrder> getDebitOrderById(UUID debitOrderId) {
        return debitOrderRepository.findById(debitOrderId);
    }

    public List<DebitOrderResponse> retrieveDebitOrders(UUID creditAccountId, Pageable pageable) {
        Optional<Account> acc = accountRepository.findById(creditAccountId);
        if (acc.isPresent()) {
            List<DebitOrder> cOrders = debitOrderRepository.findByCreditAccount(acc.get().getAccountId(), pageable);
            List<DebitOrderResponse> dList = new ArrayList<>();
            cOrders.forEach(debitOrder -> {
                Optional<Account> creditAccount = accountRepository.findById(debitOrder.getCreditAccountId());
                Optional<Account> debitAccount = accountRepository.findById(debitOrder.getDebitAccountId());
                if (creditAccount.isPresent() && debitAccount.isPresent()) {
                    DebitOrderResponse debitOrderResponse = new DebitOrderResponse(
                        debitOrder.getDebitOrderId(),
                        creditAccount.get().getAccountName(),
                        debitAccount.get().getAccountName(),
                        debitOrder.getDebitOrderCreatedDate(),
                        debitOrder.getDebitOrderAmount(),
                        debitOrder.getDebitOrderReceiverRef(),
                        debitOrder.getDebitOrderSenderRef(),
                        debitOrder.isDebitOrderDisabled()
                    );
                    dList.add(debitOrderResponse);
                }
            });
            return dList;
        }
        return List.of();
    }

    public List<DebitOrderResponse> saveDebitOrders(List<DebitOrderRequest> dbOrders) {
        List<DebitOrderResponse> response = new ArrayList<>();

        for (DebitOrderRequest dbOrder : dbOrders) {
            DebitOrder dbo = new DebitOrder();
            DebitOrderResponse res = new DebitOrderResponse();

            Optional<Account> dbAcc = accountRepository.findByAccountName(dbOrder.getDebitAccountName());
            Optional<Account> crAcc = accountRepository.findByAccountName(dbOrder.getCreditAccountName());

            if (dbAcc.isPresent() && crAcc.isPresent()) {
                dbo.setCreditAccountId(crAcc.get().getAccountId());
                res.setCreditAccountName(crAcc.get().getAccountName());

                dbo.setDebitAccountId(dbAcc.get().getAccountId());
                res.setDebitAccountName(dbAcc.get().getAccountName());

                dbo.setDebitOrderAmount(dbOrder.getAmount());
                res.setAmount(dbOrder.getAmount());

                dbo.setDebitOrderCreatedDate("Date from hand of zeus");
                res.setCreationDate("Date from hand ");

                dbo.setDebitOrderReceiverRef(dbOrder.getCreditRef());
                res.setReceiverRef(dbOrder.getCreditRef());

                dbo.setDebitOrderSenderRef(dbOrder.getDebitRef());
                res.setSenderRef(dbOrder.getDebitRef());

                dbo.setDebitOrderDisabled(false);
                res.setDisabled(false);

                DebitOrder savedDbo = debitOrderRepository.save(dbo);
                res.setId(savedDbo.getDebitOrderId());
                response.add(res);
            }
        }
        return response;
    }

    public List<DebitOrderTransaction> getDebitOrderTransactions() {
        return debitOrderRepository.getDebitOrderTransactions();
    }
}
