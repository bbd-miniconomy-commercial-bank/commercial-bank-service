-- liquibase formatted sql

-- changeset ryanbasiltrickett:create-account-delegation-table
CREATE TABLE account_delegation (
    account_delegation_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    account_id UUID NOT NULL,
    delegated_account_id UUID NOT NULL
);
-- rollback DROP TABLE account_delegation