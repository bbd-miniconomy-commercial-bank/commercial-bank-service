-- liquibase formatted sql

-- changeset devwasabi2:create-debit-order-transactions-table
CREATE TABLE debit_order_transaction (
    debit_order_transaction_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    debit_order_id UUID NOT NULL,
    transaction_id UUID NOT NULL
);
-- rollback DROP TABLE debit_order_transaction