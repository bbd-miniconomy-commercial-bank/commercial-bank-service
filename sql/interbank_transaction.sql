-- liquibase formatted sql

-- changeset ryanbasiltrickett:create-intebank-transaction-status-enum
CREATE TYPE interbank_transaction_status_enum AS ENUM ('failed', 'processing', 'complete');
-- rollback DROP TYPE interbank_transaction_status_enum

-- changeset ryanbasiltrickett:create-intebank-transaction-table
CREATE TABLE interbank_transaction (
    interbank_transaction_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    transaction_id UUID,
    external_account_id VARCHAR(50),
    interbank_transaction_status interbank_transaction_status_enum NOT NULL
);
-- rollback DROP TABLE interbank_transaction