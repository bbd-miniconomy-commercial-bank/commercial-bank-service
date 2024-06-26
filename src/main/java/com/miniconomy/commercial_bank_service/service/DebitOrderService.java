package com.miniconomy.commercial_bank_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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


  public DebitOrderService(DebitOrderRepository debitOrderRepository, AccountRepository accountRepository)
  {
    this.debitOrderRepository = debitOrderRepository;
    this.accountRepository = accountRepository;
  }
  
  public List<DebitOrderResponse> retrieveDebitOrders(UUID creditAccountId, Pageable pageable)
  {
    List<DebitOrder> debitOrders = debitOrderRepository.findByCreditAccountId(creditAccountId, pageable);
    return debitOrders.stream().map(debitOrder -> {
      Optional<Account> creditAccount = accountRepository.findById(debitOrder.getCreditAccountId());
      Optional<Account> debitAccount = accountRepository.findById(debitOrder.getDebitAccountId());

      DebitOrderResponse debitOrderResponse = new DebitOrderResponse(creditAccount.get().getAccountName(), debitAccount.get().getAccountName(), debitOrder.getDebitOrderCreatedDate(), debitOrder.getDebitOrderAmount() , debitOrder.getDebitOrderReceiverRef(), debitOrder.getDebitOrderSenderRef());
      return debitOrderResponse;
    }).collect(Collectors.toList());
  }
}
