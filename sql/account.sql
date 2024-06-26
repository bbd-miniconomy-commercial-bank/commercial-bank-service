-- liquibase formatted sql

-- changeset devwasabi2:create-account-table
CREATE TABLE account (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    account_name VARCHAR(50) NOT NULL
);