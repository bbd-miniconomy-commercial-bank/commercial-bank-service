-- liquibase formatted sql

-- changeset devwasabi2:create-loan-table
CREATE TABLE loan (
    loan_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    account_id UUID NOT NULL,
    loan_amount BIGINT NOT NULL,
    loan_type VARCHAR(20) NOT NULL,
    loan_created_date CHAR(8) NOT NULL
);
-- rollback DROP TABLE loan