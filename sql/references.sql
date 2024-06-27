-- liquibase formatted sql

-- changeset devwasabi2:add-fk-constraints-debit-order-credit-acc
ALTER TABLE debit_order
ADD CONSTRAINT fk_db_order_credit_account_id FOREIGN KEY (credit_account_id) REFERENCES account(account_id);
-- rollback ALTER TABLE debit_order DROP CONSTRAINT fk_db_order_credit_account_id

-- changeset devwasabi2:add-fk-constraints-debit-order-debit-acc
ALTER TABLE debit_order
ADD CONSTRAINT fk_db_order_debit_account_id FOREIGN KEY (debit_account_id) REFERENCES account(account_id);
-- rollback ALTER TABLE debit_order DROP CONSTRAINT fk_db_order_debit_account_id

-- changeset devwasabi2:add-fk-constraints-transaction_account_credit_account_id
ALTER TABLE transaction 
ADD CONSTRAINT fk_transaction_account_credit_account_id FOREIGN KEY (credit_account_id) REFERENCES account(account_id);
-- rollback ALTER TABLE transaction DROP CONSTRAINT fk_transaction_account_credit_account_id

-- changeset devwasabi2:add-fk-constraints-transaction-debit-acc
ALTER TABLE transaction
ADD CONSTRAINT fk_transaction_account_debit_account_id FOREIGN KEY (debit_account_id) REFERENCES account(account_id);
-- rollback ALTER TABLE transaction DROP CONSTRAINT fk_transaction_account_debit_account_id

-- changeset devwasabi2:add-fk-constraints-loan-account
ALTER TABLE loan
ADD CONSTRAINT fk_loan_account_account_id FOREIGN KEY (account_id) REFERENCES account(account_id);
-- rollback ALTER TABLE loan DROP CONSTRAINT fk_loan_account_account_id

-- changeset devwasabi2:add-fk-constraints-loan-interest-loan
ALTER TABLE loan_interest
ADD CONSTRAINT fk_loan_interest_loan_loan_id FOREIGN KEY (loan_id) REFERENCES loan(loan_id);
-- rollback ALTER TABLE loan_interest DROP CONSTRAINT fk_loan_interest_loan_loan_id;

-- changeset devwasabi2:add-fk-constraints-loan-transaction-loan
ALTER TABLE loan_transaction
ADD CONSTRAINT fk_loan_transaction_loan_loan_id FOREIGN KEY (loan_id) REFERENCES loan(loan_id);
-- rollback ALTER TABLE loan_transaction DROP CONSTRAINT fk_loan_transaction_loan_loan_id;

-- changeset devwasabi2:add-fk-constraints-loan-transaction-transaction
ALTER TABLE loan_transaction
ADD CONSTRAINT fk_loan_transaction_transaction_transaction_id FOREIGN KEY (transaction_id) REFERENCES transaction(transaction_id);
-- rollback ALTER TABLE loan_transaction DROP CONSTRAINT fk_loan_transaction_transaction_transaction_id;

-- changeset devwasabi2:add-fk-constraints-db-order-transaction-db-order
ALTER TABLE debit_order_transaction
ADD CONSTRAINT fk_db_order_transactions_debit_order_id FOREIGN KEY (debit_order_id) REFERENCES debit_order(debit_order_id);
-- rollback ALTER TABLE debit_order_transaction DROP CONSTRAINT fk_db_order_transactions_debit_order_id

-- changeset devwasabi2:add-fk-constraints-db-order-transaction-transaction
ALTER TABLE debit_order_transaction
ADD CONSTRAINT fk_db_order_transactions_transaction_id FOREIGN KEY (transaction_id) REFERENCES transaction(transaction_id);
-- rollback ALTER TABLE debit_order_transaction DROP CONSTRAINT fk_db_order_transactions_transaction_id

-- changeset ryanbasiltrickett:add-fk-constraints-account-delegation-account
ALTER TABLE account_delegation
ADD CONSTRAINT fk_account_delegation_account_id FOREIGN KEY (account_id) REFERENCES account(account_id);
-- rollback ALTER TABLE debit_order_transaction DROP CONSTRAINT fk_db_order_transactions_transaction_id

-- changeset ryanbasiltrickett:add-fk-constraints-account-delegation-delegated-account
ALTER TABLE account_delegation
ADD CONSTRAINT fk_account_delegation_delegated_account_id FOREIGN KEY (delegated_account_id) REFERENCES account(account_id);
-- rollback ALTER TABLE debit_order_transaction DROP CONSTRAINT fk_db_order_transactions_transaction_id