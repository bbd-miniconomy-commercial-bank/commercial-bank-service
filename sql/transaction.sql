CREATE TYPE transaction_status_enum AS ENUM ('pending', 'completed');

CREATE TABLE transaction (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_id INTEGER NOT NULL,
    transaction_status transaction_status_enum NOT NULL,
    transaction_amount NUMERIC(10, 2) NOT NULL,
    transaction_ref VARCHAR(50) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);