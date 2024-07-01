package com.miniconomy.commercial_bank_service.financial_management.repository;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebitOrderRepository extends JpaRepository<DebitOrder, UUID>
{
  //List<DebitOrder> findByCreditAccountId(UUID creditAccountId, Pageable pageable);
  //List<DebitOrder> findByDebitAccount(Account acc, Pageable page);
  List<DebitOrder> findByCreditAccount(Account acc, Pageable page);
  Optional<DebitOrder> findByIdAndCreditAccount(UUID id, Account creditAccount);

  Optional<DebitOrder> findById(UUID id);
}
