package com.miniconomy.commercial_bank_service.entity;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "debit_order")
public class DebitOrder
{
  @Id
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  @Column(name = "debit_order_id")
  private UUID debitOrderId;

  @ManyToOne(cascade = CascadeType.ALL) 
  @JoinColumn(name = "credit_account_id")
  private Account creditAccount;

  @ManyToOne(cascade = CascadeType.ALL) 
  @JoinColumn(name = "debit_account_id")
  private Account debitAccount;

  @Column(name = "debit_order_created_date")
  private String debitOrderCreatedDate;

  @Column(name = "debit_order_amount")
  private BigDecimal debitOrderAmount;

  @Column(name = "debit_order_receiver_ref")
  private String debitOrderReceiverRef;

  @Column(name = "debit_order_sender_ref")
  private String debitOrderSenderRef;
  
  @Column(name = "debit_order_disabled")
  private boolean debitOrderDisabled;

}
