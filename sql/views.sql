-- liquibase formatted sql

-- changeset ryanbasiltrickett:create-account_balances-view
CREATE VIEW account_balances_view AS
SELECT 
    a.account_id,
    a.account_name,
    a.account_cn,
    a.account_notification_endpoint,
    COALESCE(SUM(CASE WHEN t.credit_account_id = a.account_id THEN t.transaction_amount ELSE 0 END), 0) - 
    COALESCE(SUM(CASE WHEN t.debit_account_id = a.account_id THEN t.transaction_amount ELSE 0 END), 0) AS account_balance
FROM 
    account a
LEFT JOIN 
    transaction t
ON 
    a.account_id = t.debit_account_id OR a.account_id = t.credit_account_id
GROUP BY 
    a.account_id;
-- rollback DROP VIEW IF EXISTS credit_account_total;

-- changeset thashilbbd:create-debit-order-transactions-view
CREATE VIEW debit_order_transactions_view AS
SELECT 
    dot.debit_order_transaction_id,
    dot.debit_order_id,
    dot.transaction_id,
    debit_account.account_name AS debit_account_name,
    credit_account.account_name AS credit_account_name,
    t.transaction_debit_ref,
    t.transaction_credit_ref,
    t.transaction_amount,
    t.transaction_date,
    t.transaction_status
FROM 
    debit_order_transaction dot
    JOIN debit_order "do" ON dot.debit_order_id = "do".debit_order_id
    JOIN transaction t ON dot.transaction_id = t.transaction_id
    JOIN account credit_account ON "do".credit_account_id = credit_account.account_id
    JOIN account debit_account ON "do".debit_account_id = debit_account.account_id;
-- rollback DROP VIEW IF EXISTS debit_order_transactions

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


-- changeset ryanbasiltrickett:account-debit-order-view
CREATE VIEW account_debit_order_view AS 
SELECT
    "do".debit_order_id,
    a_debit.account_name AS debit_account_name,
    a_credit.account_name AS credit_account_name,
    "do".debit_order_debit_ref,
    "do".debit_order_credit_ref,
    "do".debit_order_amount,
    "do".debit_order_created_date,
    "do".debit_order_disabled
FROM
    debit_order "do"
JOIN
    account a_debit ON "do".debit_account_id = a_debit.account_id
JOIN
    account a_credit ON "do".credit_account_id = a_credit.account_id;
-- rollback DROP VIEW IF EXISTS account_debit_order_view;

-- changeset ryanbasiltrickett:account-loan-view
CREATE VIEW account_loan_view AS 
SELECT
    l.loan_id,
    a.account_name,
    l.loan_amount,
    l.debit_order_credit_ref,
    l.loan_type,
    l.loan_created_date
FROM
    loan l
JOIN
    account a ON l.account_id = a.account_id;
-- rollback DROP VIEW IF EXISTS account_loan_view;

-- changeset ryanbasiltrickett:account-transaction-view
CREATE VIEW account_transaction_view AS 
SELECT
    t.debit_order_id,
    a_debit.account_name AS debit_account_name,
    a_credit.account_name AS credit_account_name,
    t.transaction_debit_ref,
    t.transaction_credit_ref,
    t.transaction_amount,
    t.transaction_date,
    t.transaction_status
FROM
    "transaction" t
JOIN
    account a_debit ON t.debit_account_id = a_debit.account_id
JOIN
    account a_credit ON t.credit_account_id = a_credit.account_id;
-- rollback DROP VIEW IF EXISTS account_transaction_view;