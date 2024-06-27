-- liquibase formatted sql

-- changeset devwasabi2:create-transaction-status-enum
CREATE TYPE transaction_status_enum AS ENUM ('pending', 'completed');
-- rollback DROP TYPE transaction_status_enum

-- changeset devwasabi2:create-transaction-table
CREATE TABLE transaction (
    transaction_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    credit_account_id UUID NOT NULL,
    debit_account_id UUID NOT NULL,
    transaction_date CHAR(8) NOT NULL,
    transaction_amount NUMERIC(13, 3) NOT NULL,
    credit_ref VARCHAR(100) NOT NULL,
    debit_ref VARCHAR(100) NOT NULL,
    transaction_status transaction_status_enum NOT NULL
);
-- rollback DROP TABLE transaction

-- changeset devwasabi2:alter-transaction_amount
ALTER TABLE transaction
ALTER COLUMN transaction_amount TYPE BIGINT USING transaction_amount::BIGINT,
ALTER COLUMN transaction_amount SET NOT NULL;
-- rollback ALTER TABLE transaction DROP COLUMN transaction_amount