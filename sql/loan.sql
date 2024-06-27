-- liquibase formatted sql

-- changeset devwasabi2:create-loan-type-enum
CREATE TYPE loan_type_enum AS ENUM ('short-term', 'long-term');
-- rollback DROP TYPE loan_type_enum

-- changeset devwasabi2:create-loan-table
CREATE TABLE loan (
    loan_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    account_id UUID NOT NULL,
    loan_amount NUMERIC(13, 3) NOT NULL,
    loan_type loan_type_enum NOT NULL
);
-- rollback DROP TABLE loan

-- changeset devwasabi2:alter-loan-amount
ALTER TABLE loan
ALTER COLUMN loan_amount TYPE BIGINT USING loan_amount::BIGINT,
ALTER COLUMN loan_amount SET NOT NULL;
-- rollback ALTER TABLE loan DROP COLUMN loan_amount