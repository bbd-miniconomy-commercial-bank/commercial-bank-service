package com.miniconomy.commercial_bank_service.financial_management.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account_delegation")
public class AccountDelegation {
    
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "account_delegation_id")
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL) 
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(cascade = CascadeType.ALL) 
    @JoinColumn(name = "delegated_account_id")
    private Account delegatedAccount;
}
