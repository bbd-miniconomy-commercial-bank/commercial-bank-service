package com.miniconomy.commercial_bank_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;

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
  
  public List<DebitOrder> retrieveDebitOrders(Long creditAccountId)
  {
    return debitOrderRepository.findByCreditAccountId(creditAccountId);
  }

  public boolean disableDebitOrder(Long debitOrderId) {
    Optional<DebitOrder> debitOrderOptional = debitOrderRepository.findById(debitOrderId);
    if (debitOrderOptional.isPresent()) {
      DebitOrder debitOrder = debitOrderOptional.get();
      debitOrder.setDisabled(true);
      debitOrderRepository.save(debitOrder);
      return true;
    }
    return false;
  }

  public Optional<DebitOrder> getDebitOrderById(Long debitOrderId) {
    return debitOrderRepository.findById(debitOrderId);
  }

  public Optional<DebitOrder> updateDebitOrder(Long debitOrderId, DebitOrder updatedDebitOrder) {
    Optional<DebitOrder> debitOrderOptional = debitOrderRepository.findById(debitOrderId);
    if (debitOrderOptional.isPresent()) {
      DebitOrder existingDebitOrder = debitOrderOptional.get();

      if (updatedDebitOrder.getCreditAccountId() != null && 
          !updatedDebitOrder.getCreditAccountId().equals(existingDebitOrder.getCreditAccountId())) {
        existingDebitOrder.setCreditAccountId(updatedDebitOrder.getCreditAccountId());
      }

      if (updatedDebitOrder.getDebitAccountId() != null && 
          !updatedDebitOrder.getDebitAccountId().equals(existingDebitOrder.getDebitAccountId())) {
        existingDebitOrder.setDebitAccountId(updatedDebitOrder.getDebitAccountId());
      }

      if (updatedDebitOrder.getDebitOrderAmount() != null && 
          !updatedDebitOrder.getDebitOrderAmount().equals(existingDebitOrder.getDebitOrderAmount())) {
        existingDebitOrder.setDebitOrderAmount(updatedDebitOrder.getDebitOrderAmount());
      }

      if (updatedDebitOrder.getDebitOrderReceiverRef() != null && 
          !updatedDebitOrder.getDebitOrderReceiverRef().equals(existingDebitOrder.getDebitOrderReceiverRef())) {
        existingDebitOrder.setDebitOrderReceiverRef(updatedDebitOrder.getDebitOrderReceiverRef());
      }

      if (updatedDebitOrder.getDebitOrderSenderRef() != null && 
          !updatedDebitOrder.getDebitOrderSenderRef().equals(existingDebitOrder.getDebitOrderSenderRef())) {
        existingDebitOrder.setDebitOrderSenderRef(updatedDebitOrder.getDebitOrderSenderRef());
      }

      existingDebitOrder.setDisabled(updatedDebitOrder.getDisabled());
      
      debitOrderRepository.save(existingDebitOrder);
      return Optional.of(existingDebitOrder);
    }
    return Optional.empty();
  }
}
