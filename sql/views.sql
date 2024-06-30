-- liquibase formatted sql

-- changeset devwasabi2:create-debit-account-transactions-total-view
CREATE VIEW debit_account_total AS
SELECT 
    debit_account_id,
    SUM(transaction_amount)::BIGINT AS total_amount
FROM 
    transaction
GROUP BY 
    debit_account_id;
-- rollback DROP VIEW IF EXISTS debit_account_total;

-- changeset devwasabi2:create-credit-account-transactions-total-view
CREATE VIEW credit_account_total AS 
SELECT 
    credit_account_id,
    SUM(transaction_amount)::BIGINT AS total_amount
FROM 
    transaction
GROUP BY 
    credit_account_id;
-- rollback DROP VIEW IF EXISTS credit_account_total;

-- changeset thashilbbd:create-debit-order-transactions-view
CREATE VIEW debit_order_transactions AS
SELECT 
    dot.debit_order_transaction_id,
    dot.debit_order_id,
    dot.transaction_id,
    "do".credit_account_id,
    credit_account.account_name AS credit_account_name,
    "do".debit_account_id,
    debit_account.account_name AS debit_account_name,
    t.transaction_date,
    t.transaction_amount,
    t.credit_ref,
    t.debit_ref,
    t.transaction_status
FROM 
    debit_order_transaction dot
    JOIN debit_order "do" ON dot.debit_order_id = "do".debit_order_id
    JOIN transaction t ON dot.transaction_id = t.transaction_id
    JOIN account credit_account ON "do".credit_account_id = credit_account.account_id
    JOIN account debit_account ON "do".debit_account_id = debit_account.account_id;
-- rollback DROP VIEW debit_order_transactions

-- changeset tawanda-bbd:create-loan-interest-view
CREATE VIEW loan_interest_view AS
SELECT 
    l.loan_id,
    l.account_id,
    l.loan_amount,
    l.loan_type,
    li.loan_interest_id,
    li.loan_interest_rate,
    li.loan_interest_amount,
    li.loan_interest_date
FROM 
    loan l
JOIN 
    loan_interest li ON l.loan_id = li.loan_id;
-- rollback DROP VIEW IF EXISTS loan_interest_view;

-- changeset tawanda-bbd:create-loan-transactions-view
CREATE VIEW loan_transactions_view AS
SELECT 
    l.loan_id,
    l.account_id,
    l.loan_amount,
    l.loan_type,
    lt.loan_transaction_id,
    t.transaction_id,
    t.credit_account_id,
    t.debit_account_id,
    t.transaction_date,
    t.transaction_amount,
    t.credit_ref,
    t.debit_ref,
    t.transaction_status
FROM 
    loan l
JOIN 
    loan_transaction lt ON l.loan_id = lt.loan_id
JOIN 
    transaction t ON lt.transaction_id = t.transaction_id;
-- rollback DROP VIEW IF EXISTS loan_transactions_view;