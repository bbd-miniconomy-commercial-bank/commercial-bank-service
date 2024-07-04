-- liquibase formatted sql

-- changeset devwasabi2:create-transaction-status-enum
CREATE TYPE transaction_status_enum AS ENUM ('failed', 'pending', 'complete');
-- rollback DROP TYPE transaction_status_enum

-- changeset devwasabi2:create-transaction-table
CREATE TABLE transaction (
    transaction_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    debit_account_id UUID NOT NULL,
    credit_account_id UUID NOT NULL,
    transaction_debit_ref VARCHAR(50) NOT NULL,
    transaction_credit_ref VARCHAR(50) NOT NULL,
    transaction_amount BIGINT NOT NULL,
    transaction_date CHAR(8) NOT NULL,
    transaction_status transaction_status_enum NOT NULL
);
-- rollback DROP TABLE transaction

-- changeset ryanbasiltrickett:update-date-transaction-table
ALTER TABLE transaction
ALTER COLUMN transaction_date VARCHAR
-- rollback ALTER TABLE transaction MODIFY COLUMN transaction_date CHAR(8)