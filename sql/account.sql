-- liquibase formatted sql

-- changeset devwasabi2:create-account-table
CREATE TABLE account (
    account_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    account_name VARCHAR(50) NOT NULL
);
-- rollback DROP TABLE account

-- changeset devwasabi2:insert-accounts
INSERT INTO account (account_name) VALUES 
('retail-bank'),
('commercial-bank'),
('health-insurance'),
('life-insurance'),
('short-term-insurance'),
('health-care'),
('central-revenue-service'),
('labour-broker'),
('stock-exchange'),
('real-estate-property-sales'),
('real-estate-rentals'),
('short-term-lender'),
('home-loans'),
('electronics-retailer'),
('food-retailer');
-- rollback DELETE FROM account