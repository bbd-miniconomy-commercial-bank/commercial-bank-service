-- liquibase formatted sql

-- changeset devwasabi2:create-transaction-table
CREATE TABLE transaction (
    transaction_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    credit_account_id UUID NOT NULL,
    debit_account_id UUID NOT NULL,
    transaction_date CHAR(8) NOT NULL,
    transaction_amount BIGINT NOT NULL,
    credit_ref VARCHAR(50) NOT NULL,
    debit_ref VARCHAR(50) NOT NULL,
    transaction_status VARCHAR(20) NOT NULL
);
-- rollback DROP TABLE transaction