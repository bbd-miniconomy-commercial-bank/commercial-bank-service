package com.miniconomy.commercial_bank_service.admin_portal.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

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
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  @Column(name = "account_id")
  private UUID id;

  @Size(max = 50, message = "Account name must be less than or equal to 50 characters")
  @Column(name = "account_name")
  private String accountName;

  @Size(max = 50, message = "Account CN must be less than or equal to 50 characters")
  @Column(name = "account_cn")
  private String accountCN;
  
  @Size(max = 125, message = "Account Notifiaction Endpoint must be less than or equal to 125 characters")
  @Column(name = "account_notification_endpoint")
  private String accountNotificationEndpoint;
  
}
