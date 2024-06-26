-- liquibase formatted sql

-- changeset devwasabi2:create-loan-transaction-table
CREATE TABLE loantransaction (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    loan_id INTEGER NOT NULL,
    transaction_id INTEGER NOT NULL,
    FOREIGN KEY (loan_id) REFERENCES loan(id),
    FOREIGN KEY (transaction_id) REFERENCES transaction(id)
);
-- rollback DROP TABLE loantransaction