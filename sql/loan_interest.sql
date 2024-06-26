-- liquibase formatted sql

-- changeset devwasabi2:create-loan-interest-table
CREATE TABLE loan_interest (
    loan_interest_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    loan_id UUID NOT NULL,
    loan_interest_rate NUMERIC(5, 2) NOT NULL,
    loan_interest_amount NUMERIC(13, 3) NOT NULL,
    loan_interest_date CHAR(8) NOT NULL
);
-- rollback DROP TABLE loan_interest

