-- liquibase formatted sql

-- changeset devwasabi2:create-loan-type-enum
CREATE TYPE loan_type_enum AS ENUM ('short_term', 'long_term');
-- rollback DROP TYPE loan_type_enum

-- changeset devwasabi2:create-loan-table
CREATE TABLE loan (
    loan_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    account_id UUID NOT NULL,
    loan_amount BIGINT NOT NULL,
    loan_type loan_type_enum NOT NULL,
    loan_created_date CHAR(8) NOT NULL
);
-- rollback DROP TABLE loan



-- changeset ryanbasiltrickett:update-date-loan-table
ALTER TABLE loan
ALTER COLUMN loan_created_date TYPE VARCHAR(100)
-- rollback ALTER TABLE loan ALTER COLUMN loan_created_date TYPE CHAR(8)