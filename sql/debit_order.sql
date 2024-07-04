-- liquibase formatted sql

-- changeset devwasabi2:create-debit-order-table
CREATE TABLE debit_order (
    debit_order_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    debit_account_id UUID NOT NULL,
    credit_account_name VARCHAR(36) NOT NULL,
    debit_order_debit_ref VARCHAR(50) NOT NULL,
    debit_order_credit_ref VARCHAR(50) NOT NULL,
    debit_order_amount BIGINT NOT NULL,
    debit_order_created_date CHAR(8) NOT NULL,
    debit_order_disabled BOOLEAN NOT NULL
);
-- rollback DROP TABLE debit_order

-- changeset ryanbasiltrickett:update-date-debit-order-table
ALTER TABLE debit_order
ALTER COLUMN debit_order_created_date TYPE VARCHAR(100)
-- rollback ALTER TABLE debit_order ALTER COLUMN debit_order_created_date TYPE CHAR(8)