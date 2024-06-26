-- liquibase formatted sql

-- changeset devwasabi2:create-transaction-status-enum
CREATE TYPE transaction_status_enum AS ENUM ('pending', 'completed');
-- rollback DROP TYPE transaction_status_enum

-- changeset devwasabi2:create-transaction-table
CREATE TABLE transaction (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    credit_acc_id INTEGER NOT NULL,
    debit_acc_id INTEGER NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    transaction_amount NUMERIC(10, 2) NOT NULL,
    credit_ref VARCHAR(50) NOT NULL,
    debit_ref VARCHAR(50) NOT NULL,
    transaction_status transaction_status_enum NOT NULL,
    FOREIGN KEY (credit_acc_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (debit_acc_id) REFERENCES account(id) ON DELETE CASCADE
);
-- rollback DROP TABLE transaction