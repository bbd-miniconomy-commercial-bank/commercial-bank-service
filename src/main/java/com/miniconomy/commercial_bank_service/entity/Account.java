package com.miniconomy.commercial_bank_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account_id")
  private Long id;

  @Size(max = 50, message = "Account name must be less than or equal to 50 characters")
  @Column(name = "account_name")
  private String accountName;
  
}
