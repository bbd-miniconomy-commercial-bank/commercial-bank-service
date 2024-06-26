-- liquibase formatted sql

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