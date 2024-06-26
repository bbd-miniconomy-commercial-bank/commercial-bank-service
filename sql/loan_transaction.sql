-- liquibase formatted sql

-- changeset devwasabi2:create-loan-transaction-table
CREATE TABLE loan_transaction (
    loan_transaction_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    loan_id UUID NOT NULL,
    transaction_id UUID NOT NULL
);
-- rollback DROP TABLE loan_transaction