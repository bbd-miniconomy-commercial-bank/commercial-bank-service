-- liquibase formatted sql

-- changeset devwasabi2:create-transaction-table
CREATE TABLE transaction (
    transaction_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    debit_account_id UUID NOT NULL,
    credit_account_id UUID NOT NULL,
    transaction_debit_ref VARCHAR(50) NOT NULL,
    transaction_credit_ref VARCHAR(50) NOT NULL,
    transaction_amount BIGINT NOT NULL,
    transaction_date CHAR(8) NOT NULL,
    transaction_status VARCHAR(20) NOT NULL
);
-- rollback DROP TABLE transaction