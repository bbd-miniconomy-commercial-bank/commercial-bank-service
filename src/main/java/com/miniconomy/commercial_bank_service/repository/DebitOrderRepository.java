package com.miniconomy.commercial_bank_service.repository;

import com.miniconomy.commercial_bank_service.entity.DebitOrder;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebitOrderRepository extends JpaRepository<DebitOrder, UUID>
{
  List<DebitOrder> findByCreditAccountId(UUID creditAccountId, Pageable pageable);
}
