CREATE TABLE debitorder (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sender_acc_id INTEGER NOT NULL,
    receiver_acc_id INTEGER NOT NULL,
    sender_ref VARCHAR(50) NOT NULL,
    receiver_ref VARCHAR(50) NOT NULL,
    db_order_amount NUMERIC(10, 2) NOT NULL,
    db_order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_acc_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_acc_id) REFERENCES account(id) ON DELETE CASCADE
);