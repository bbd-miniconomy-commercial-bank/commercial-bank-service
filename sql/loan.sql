-- liquibase formatted sql

-- changeset devwasabi2:create-loan-type-enum
CREATE TYPE loan_type_enum AS ENUM ('short-term', 'long-term');
-- rollback DROP TYPE loan_type_enum

-- changeset devwasabi2:create-loan-table
CREATE TABLE loan (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_id INTEGER NOT NULL,
    loan_amount NUMERIC(10, 2) NOT NULL,
    loan_type loan_type_enum NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);
-- rollback DROP TABLE loan