package com.miniconomy.commercial_bank_service.financial_management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.miniconomy.commercial_bank_service.financial_management.entity.Account;
import com.miniconomy.commercial_bank_service.financial_management.entity.DebitOrder;
import com.miniconomy.commercial_bank_service.financial_management.repository.AccountRepository;
import com.miniconomy.commercial_bank_service.financial_management.repository.DebitOrderRepository;
import com.miniconomy.commercial_bank_service.financial_management.request.DebitOrderRequest;
import com.miniconomy.commercial_bank_service.financial_management.response.DebitOrderResponse;

@Service
public class DebitOrderService
{
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  private final DebitOrderRepository debitOrderRepository;
  private final AccountRepository accountRepository;

  public DebitOrderService(DebitOrderRepository debitOrderRepository, AccountRepository accRepo)
  {
    this.debitOrderRepository = debitOrderRepository;
    this.accountRepository = accRepo;
  }

  public boolean disableDebitOrder(UUID debitOrderId) {
    Optional<DebitOrder> debitOrderOptional = debitOrderRepository.findById(debitOrderId);
    if (debitOrderOptional.isPresent()) {
      DebitOrder debitOrder = debitOrderOptional.get();
      debitOrder.setDebitOrderDisabled(true);
      debitOrderRepository.save(debitOrder);
      return true;
    }
    return false;
  }

  public Optional<DebitOrder> updateDebitOrder(UUID id, DebitOrderRequest dbOrder, String creditAccountName) {
    Optional<Account> creditAccountOptional = accountRepository.findByAccountName(creditAccountName);
    
    String sql = "SELECT * FROM debit_order WHERE debit_order_id = :id AND credit_account_id = :creditAccountId";
    SqlParameterSource parameters = new MapSqlParameterSource()
      .addValue("id", id)
      .addValue("creditAccountId", creditAccountOptional.get().getId());
    DebitOrder d = jdbcTemplate.queryForObject(sql, parameters, DebitOrder.class);

    Optional<Account> debAcc = accountRepository.findByAccountName(dbOrder.getDebitAccountName());

    if (d != null && creditAccountOptional.isPresent() && debAcc.isPresent()) {
      d.setCreditAccount(creditAccountOptional.get());
      d.setDebitAccount(debAcc.get());
      d.setDebitOrderAmount(dbOrder.getAmount());
      d.setDebitOrderCreatedDate(java.time.LocalDate.now().toString().replace("-", ""));
      d.setDebitOrderReceiverRef(dbOrder.getCreditRef());
      d.setDebitOrderSenderRef(dbOrder.getDebitRef());
      debitOrderRepository.save(d);
      return Optional.of(d);
    }
    return Optional.empty();
  }

  public Optional<DebitOrder> getDebitOrderById(UUID debitOrderId, String creditAccountName) {
    Optional<Account> creditAccountOptional = accountRepository.findByAccountName(creditAccountName);
    
    String sql = "SELECT * FROM debit_order WHERE debit_order_id = :id AND credit_account_id = :creditAccountId";
    SqlParameterSource parameters = new MapSqlParameterSource()
      .addValue("id", debitOrderId)
      .addValue("creditAccountId", creditAccountOptional.get().getId());
    DebitOrder d = jdbcTemplate.queryForObject(sql, parameters, DebitOrder.class);

    return Optional.of(d);
  }
  
  public List<DebitOrderResponse> retrieveDebitOrders(String creditAccountName, Pageable pageable)
  {
    Optional<Account> acc = accountRepository.findByAccountName(creditAccountName);
    if (acc.isPresent()) {
      List<DebitOrder> cOrders = debitOrderRepository.findByCreditAccount(acc.get(), pageable);
      //List<DebitOrder> dOrders = debitOrderRepository.findByCreditAccount(acc.get(), pageable);
      //cOrders.addAll(dOrders);
      List<DebitOrderResponse> dList = new ArrayList<>();
      cOrders.stream().forEach(debitOrder -> {
        Optional<Account> creditAccount = accountRepository.findById(debitOrder.getCreditAccount().getId());
        Optional<Account> debitAccount = accountRepository.findById(debitOrder.getDebitAccount().getId());
        DebitOrderResponse debitOrderResponse = new DebitOrderResponse(
          debitOrder.getDebitOrderId(),
          creditAccount.get().getAccountName(), 
          debitAccount.get().getAccountName(), 
          debitOrder.getDebitOrderCreatedDate(), 
          debitOrder.getDebitOrderAmount() , 
          debitOrder.getDebitOrderReceiverRef(), 
          debitOrder.getDebitOrderSenderRef(), 
          debitOrder.isDebitOrderDisabled()
        );
        dList.add(debitOrderResponse);
      });
      return dList;
    }
    return List.of(); // otherwise return an empty list
  }

  public List<DebitOrder> saveDebitOrders(List<DebitOrderRequest> dbOrders, String accountName) {
    List<DebitOrder> debitOrders = new ArrayList<>();

    for (DebitOrderRequest dbOrder : dbOrders) {

      DebitOrder dbo = new DebitOrder();
      
      Optional<Account> dbAcc = accountRepository.findByAccountName(dbOrder.getDebitAccountName());
      Optional<Account> crAcc = accountRepository.findByAccountName(accountName); 
      
      if (dbAcc.isPresent() && crAcc.isPresent()) {
        
        dbo.setCreditAccount(crAcc.get()); 
        dbo.setDebitAccount(dbAcc.get()); 
        dbo.setDebitOrderAmount(dbOrder.getAmount()); 
        dbo.setDebitOrderCreatedDate(java.time.LocalDate.now().toString().replace("-", "")); 
        dbo.setDebitOrderReceiverRef(dbOrder.getCreditRef()); 
        dbo.setDebitOrderSenderRef(dbOrder.getDebitRef()); 
        dbo.setDebitOrderDisabled(false);

        DebitOrder savedDbo = debitOrderRepository.save(dbo);
        debitOrders.add(savedDbo);
      }
    }
    return debitOrders;
  }
}
