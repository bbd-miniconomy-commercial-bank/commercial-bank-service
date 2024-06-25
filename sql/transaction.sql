CREATE TABLE transaction (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_id INTEGER NOT NULL,
    transaction_status_id INTEGER NOT NULL,
    transaction_amount NUMERIC(10, 2) NOT NULL,
    transaction_ref VARCHAR(50) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (transaction_status_id) REFERENCES transactionstatus(id) ON DELETE CASCADE
);