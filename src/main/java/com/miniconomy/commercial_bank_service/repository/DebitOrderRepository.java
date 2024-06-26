package com.miniconomy.commercial_bank_service.repository;

import com.miniconomy.commercial_bank_service.entity.DebitOrder;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DebitOrderRepository extends JpaRepository<DebitOrder, Long>
{
  List<DebitOrder> findByCreditAccountId(Long creditAccountId);
}
