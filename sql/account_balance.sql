-- liquibase formatted sql

-- changeset devwasabi2:create-debit-account-transactions-total-view
CREATE VIEW debit_account_total AS
SELECT 
    debit_account_id,
    SUM(transaction_amount) AS total_amount
FROM 
    transaction
GROUP BY 
    debit_account_id;
-- rollback DROP VIEW debit_account_total;

-- changeset devwasabi2:create-credit-account-transactions-total-view
CREATE VIEW credit_account_total AS
SELECT 
    credit_account_id,
    SUM(transaction_amount) AS total_amount
FROM 
    transaction
GROUP BY 
    credit_account_id;
-- rollback DROP VIEW credit_account_total;
