-- liquibase formatted sql

-- changeset devwasabi2:create-debit-order-table
CREATE TABLE debit_order (
    debit_order_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    credit_account_id UUID NOT NULL,
    debit_account_id UUID NOT NULL,
    debit_order_sender_ref VARCHAR(50) NOT NULL,
    debit_order_receiver_ref VARCHAR(50) NOT NULL,
    debit_order_amount BIGINT NOT NULL,
    debit_order_created_date CHAR(8) NOT NULL,
    debit_order_disabled BOOLEAN NOT NULL
);
-- rollback DROP TABLE debit_order