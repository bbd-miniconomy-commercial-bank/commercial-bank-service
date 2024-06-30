-- liquibase formatted sql

-- changeset ryanbasiltrickett:create-intebank-transaction-table
CREATE TABLE interbank_transaction (
    interbank_transaction_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    transaction_id UUID NOT NULL
);
-- rollback DROP TABLE interbank_transaction