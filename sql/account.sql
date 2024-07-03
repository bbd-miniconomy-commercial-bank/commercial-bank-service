-- liquibase formatted sql

-- changeset devwasabi2:create-account-table
CREATE TABLE account (
    account_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    account_name VARCHAR(50) UNIQUE NOT NULL,
    account_cn VARCHAR(50) UNIQUE NOT NULL,
    account_notification_endpoint VARCHAR(125) NOT NULL
);
-- rollback DROP TABLE account

-- changeset devwasabi2:insert-accounts
INSERT INTO account (account_name, account_cn, account_notification_endpoint) VALUES 
('retail-bank', 'retail_bank', ''),
('commercial-bank', 'commercial_bank', ''),
('health-insurance', 'health_insurance', ''),
('life-insurance', 'life_insurance', ''),
('short-term-insurance', 'short_term_insurance', ''),
('health-care', 'health_care', ''),
('central-revenue-service', 'central_revenue', ''),
('labour-broker', 'labour', ''),
('stock-exchange', 'stock_exchange', 'https://api.mese.projects.bbdgrad.com/transactionsCallBack'),
('real-estate-property-sales', 'real_estate_sales', ''),
('real-estate-rentals', 'real_estate_agent', ''),
('short-term-lender', 'short_term_lender', ''),
('home-loans', 'home_loans', ''),
('electronics-retailer', 'electronics_retailer', ''),
('food-retailer', 'food_retailer', '');
-- rollback DELETE FROM account