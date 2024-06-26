package com.miniconomy.commercial_bank_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
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
  
  public List<DebitOrder> retrieveDebitOrders(UUID creditAccountId, Pageable pageable)
  {
    return debitOrderRepository.findByCreditAccountId(creditAccountId, pageable);
  }
}
