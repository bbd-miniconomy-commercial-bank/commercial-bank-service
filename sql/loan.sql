CREATE TABLE loan (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_id INTEGER NOT NULL,
    debit_order_id INTEGER NOT NULL,
    loan_amount NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (debit_order_id) REFERENCES debitorder(id) ON DELETE CASCADE
);