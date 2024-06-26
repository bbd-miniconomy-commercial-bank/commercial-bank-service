-- liquibase formatted sql

-- changeset devwasabi2:create-debit-order-transactions-table
CREATE TABLE debitordertransactions (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    db_order_id INTEGER NOT NULL,
    transaction_id INTEGER NOT NULL,
    FOREIGN KEY (db_order_id) REFERENCES debitorder(id),
    FOREIGN KEY (transaction_id) REFERENCES transaction(id)
);
-- rollback DROP TABLE debitordertransactions