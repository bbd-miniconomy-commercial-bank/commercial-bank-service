package com.miniconomy.commercial_bank_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
  
  public List<DebitOrder> retrieveDebitOrders(Long creditAccountId)
  {
    return debitOrderRepository.findByCreditAccountId(creditAccountId);
  }

  public List<DebitOrder> saveDebitOrders(List<DebitOrderRequest> dbOrders) 
  {
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
        dbo.setDebitAccount(dbAcc.get());
        dbo.setDebitOrderAmount(dbOrder.getDebitOrderAmount());
        dbo.setDebitOrderCreatedDate(dbOrder.getDebitOrderCreatedDate());
        dbo.setDebitOrderReceiverRef(dbOrder.getDebitOrderReceiverRef());
        dbo.setDebitOrderSenderRef(dbOrder.getDebitOrderSenderRef());
        debitOrders.add(dbo);
      }
    }
    return debitOrderRepository.saveAll(debitOrders);
  }
}
