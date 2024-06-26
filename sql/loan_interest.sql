-- liquibase formatted sql

-- changeset devwasabi2:create-loan-interest-table
CREATE TABLE loaninterest (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    loan_id INTEGER NOT NULL,
    interest_rate INTEGER NOT NULL,
    interest_amount NUMERIC(10, 2) NOT NULL,
    datecreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (loan_id) REFERENCES loan(id)
);
-- rollback DROP TABLE loaninterest