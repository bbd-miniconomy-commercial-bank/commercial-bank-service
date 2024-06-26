package com.miniconomy.commercial_bank_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.entity.DebitOrder;
import com.miniconomy.commercial_bank_service.repository.DebitOrderRepository;

@Service
public class DebitOrderService
{
  private final DebitOrderRepository debitOrderRepository;

  public DebitOrderService(DebitOrderRepository debitOrderRepository)
  {
    this.debitOrderRepository = debitOrderRepository;
  }
  
  public List<DebitOrder> retrieveDebitOrders(Long creditAccountId)
  {
    return debitOrderRepository.findByCreditAccountId(creditAccountId);
  }

  public List<DebitOrder> saveDebitOrders(List<DebitOrder> dbOrders) 
  {
    List<DebitOrder> debitOrders = new ArrayList<>();
    for (DebitOrder dbOrder : dbOrders) {
      DebitOrder dbo = new DebitOrder();
      dbo.setCreditAccountId(dbOrder.getCreditAccountId());
      dbo.setDebitAccountId(dbOrder.getDebitAccountId());
      dbo.setDebitOrderAmount(dbOrder.getDebitOrderAmount());
      dbo.setDebitOrderCreatedDate(dbOrder.getDebitOrderCreatedDate());
      dbo.setDebitOrderReceiverRef(dbOrder.getDebitOrderReceiverRef());
      dbo.setDebitOrderSenderRef(dbOrder.getDebitOrderSenderRef());
      debitOrders.add(dbo);
    }
    return debitOrderRepository.saveAll(debitOrders);
  }
}
