CREATE TABLE debitorder (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    credit_acc_id INTEGER NOT NULL,
    debit_acc_id INTEGER NOT NULL,
    db_order_sender_ref VARCHAR(50) NOT NULL,
    db_order_receiver_ref VARCHAR(50) NOT NULL,
    db_order_amount NUMERIC(10, 2) NOT NULL,
    db_order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (credit_acc_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (debit_acc_id) REFERENCES account(id) ON DELETE CASCADE
);