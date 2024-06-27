package com.miniconomy.commercial_bank_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.dto.DebitOrderRequest;
import com.miniconomy.commercial_bank_service.entity.Account;
import com.miniconomy.commercial_bank_service.entity.DebitOrder;
import com.miniconomy.commercial_bank_service.repository.AccountRepository;
import com.miniconomy.commercial_bank_service.repository.DebitOrderRepository;
import com.miniconomy.commercial_bank_service.response.DebitOrderResponse;

@Service
public class DebitOrderService
{
  private final DebitOrderRepository debitOrderRepository;
  private final AccountRepository accountRepository;

  public DebitOrderService(DebitOrderRepository debitOrderRepository, AccountRepository accRepo)
  {
    this.debitOrderRepository = debitOrderRepository;
    this.accountRepository = accRepo;
  }
  
  public List<DebitOrderResponse> retrieveDebitOrders(UUID creditAccountId, Pageable pageable)
  {
    Optional<Account> acc = accountRepository.findById(creditAccountId);
    if (acc.isPresent()) {
      List<DebitOrder> cOrders = debitOrderRepository.findByCreditAccount(acc.get(), pageable);
      //List<DebitOrder> dOrders = debitOrderRepository.findByCreditAccount(acc.get(), pageable);
      //cOrders.addAll(dOrders);
      List<DebitOrderResponse> dList = new ArrayList<>();
      cOrders.stream().forEach(debitOrder -> {
        Optional<Account> creditAccount = accountRepository.findById(debitOrder.getCreditAccount().getId());
        Optional<Account> debitAccount = accountRepository.findById(debitOrder.getDebitAccount().getId());
        DebitOrderResponse debitOrderResponse = new DebitOrderResponse(creditAccount.get().getAccountName(), debitAccount.get().getAccountName(), debitOrder.getDebitOrderCreatedDate(), debitOrder.getDebitOrderAmount() , debitOrder.getDebitOrderReceiverRef(), debitOrder.getDebitOrderSenderRef());
        dList.add(debitOrderResponse);
      });
      return dList;
    }
    return List.of(); // otherwise return an empty list
  }

  public List<DebitOrderResponse> saveDebitOrders(List<DebitOrderRequest> dbOrders) {
    List<DebitOrder> debitOrders = new ArrayList<>();
    List<DebitOrderResponse> response = new ArrayList<>();

    for (DebitOrderRequest dbOrder : dbOrders) {

      DebitOrder dbo = new DebitOrder();
      DebitOrderResponse res = new DebitOrderResponse();
      
      Optional<Account> dbAcc = accountRepository.findByAccountName(dbOrder.getDebitAccountName());
      Optional<Account> crAcc = accountRepository.findByAccountName("commercial-bank"); // for now it's commercial-bank, but their api token should have an account-name
      
      if (dbAcc.isPresent() && crAcc.isPresent()) {
        //dbOrder.getCreditAccount().
        dbo.setCreditAccount(crAcc.get()); 
        res.setCreditAccountName(crAcc.get().getAccountName());

        dbo.setDebitAccount(dbAcc.get()); 
        res.setDebitAccountName(dbAcc.get().getAccountName());

        dbo.setDebitOrderAmount(dbOrder.getDebitOrderAmount()); 
        res.setDebitOrderAmount(dbOrder.getDebitOrderAmount());

        dbo.setDebitOrderCreatedDate(dbOrder.getDebitOrderCreatedDate()); 
        res.setDebitOrderCreatedDate(dbOrder.getDebitOrderCreatedDate());

        dbo.setDebitOrderReceiverRef(dbOrder.getDebitOrderReceiverRef()); 
        res.setDebitOrderReceiverRef(dbOrder.getDebitOrderReceiverRef());

        dbo.setDebitOrderSenderRef(dbOrder.getDebitOrderSenderRef()); 
        res.setDebitOrderSenderRef(dbOrder.getDebitOrderSenderRef());

        debitOrders.add(dbo);
        response.add(res);
      }
    }
    debitOrderRepository.saveAll(debitOrders);
    return response;
  }
}
