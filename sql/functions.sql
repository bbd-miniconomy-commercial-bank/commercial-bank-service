-- liquibase formatted sql

-- changeset ryanbasiltrickett:update_debit_order_by_account_names_func
CREATE OR REPLACE FUNCTION update_and_return_debit_order(
    p_debit_order_id VARCHAR,
    p_credit_account_name VARCHAR,
    p_debit_order_debit_ref VARCHAR,
    p_debit_order_credit_ref VARCHAR,
    p_debit_order_amount BIGINT,
    p_debit_order_disabled BOOLEAN
)
RETURNS TABLE (
    debit_order_id UUID,
    debit_account_name VARCHAR,
    credit_account_name VARCHAR,
    debit_order_debit_ref VARCHAR,
    debit_order_credit_ref VARCHAR,
    debit_order_amount BIGINT,
    debit_order_created_date CHAR(8),
    debit_order_disabled BOOLEAN
) 
LANGUAGE plpgsql
AS '
BEGIN
    -- Update the debit order
    UPDATE debit_order "do"
    SET
        credit_account_name = p_credit_account_name,
        debit_order_debit_ref = p_debit_order_debit_ref,
        debit_order_credit_ref = p_debit_order_credit_ref,
        debit_order_amount = p_debit_order_amount,
        debit_order_created_date = p_debit_order_created_date,
        debit_order_disabled = p_debit_order_disabled
    WHERE
        "do".debit_order_id = p_debit_order_id::UUID;

    -- Return the updated row
    RETURN QUERY
    SELECT
        ado.debit_order_id,
        ado.debit_account_name,
        ado.credit_account_name,
        ado.debit_order_debit_ref,
        ado.debit_order_credit_ref,
        ado.debit_order_amount,
        ado.debit_order_created_date,
        ado.debit_order_disabled
    FROM
        account_debit_order_view ado
    WHERE
        ado.debit_order_id = p_debit_order_id::UUID;
END;
';
-- rollback DROP FUNCTION update_and_return_debit_order;

-- changeset ryanbasiltrickett:insert_and_return_debit_order_func
CREATE OR REPLACE FUNCTION insert_and_return_debit_order(
    p_debit_account_name VARCHAR,
    p_credit_account_name VARCHAR,
    p_debit_order_debit_ref VARCHAR,
    p_debit_order_credit_ref VARCHAR,
    p_debit_order_amount BIGINT,
    p_debit_order_created_date VARCHAR,
    p_debit_order_disabled BOOLEAN
)
RETURNS TABLE (
    debit_order_id UUID,
    debit_account_name VARCHAR,
    credit_account_name VARCHAR,
    debit_order_debit_ref VARCHAR,
    debit_order_credit_ref VARCHAR,
    debit_order_amount BIGINT,
    debit_order_created_date CHAR(8),
    debit_order_disabled BOOLEAN
) 
LANGUAGE plpgsql
AS '
DECLARE
    v_debit_account_id UUID;
    v_new_debit_order_id UUID;
BEGIN
    -- Get the account_id for the debit account name
    SELECT a.account_id INTO v_debit_account_id
    FROM account a
    WHERE a.account_name = p_debit_account_name;

    -- Insert the new debit order and get the new ID
    INSERT INTO debit_order (
        debit_account_id,
        credit_account_name,
        debit_order_debit_ref,
        debit_order_credit_ref,
        debit_order_amount,
        debit_order_created_date,
        debit_order_disabled
    ) VALUES (
        v_debit_account_id,
        p_credit_account_name,
        p_debit_order_debit_ref,
        p_debit_order_credit_ref,
        p_debit_order_amount,
        p_debit_order_created_date,
        p_debit_order_disabled
    )
    RETURNING debit_order.debit_order_id INTO v_new_debit_order_id;

    -- Return the inserted row
    RETURN QUERY
    SELECT
        ado.debit_order_id,
        ado.debit_account_name,
        ado.credit_account_name,
        ado.debit_order_debit_ref,
        ado.debit_order_credit_ref,
        ado.debit_order_amount,
        ado.debit_order_created_date,
        ado.debit_order_disabled
    FROM
        account_debit_order_view ado
    WHERE
        ado.debit_order_id = v_new_debit_order_id;
END;
';
-- rollback DROP FUNCTION insert_and_return_debit_order;

-- changeset ryanbasiltrickett:insert_and_return_loan_func
CREATE OR REPLACE FUNCTION insert_and_return_loan(
    p_account_name VARCHAR,
    p_loan_amount BIGINT,
    p_loan_type VARCHAR,
    p_loan_created_date VARCHAR
)
RETURNS TABLE (
    loan_id UUID,
    account_name VARCHAR,
    loan_amount BIGINT,
    loan_type loan_type_enum,
    loan_created_date CHAR(8)
) 
LANGUAGE plpgsql
AS '
DECLARE
    v_account_id UUID;
    v_new_loan_id UUID;
BEGIN
    -- Get the account_id for the account name
    SELECT a.account_id INTO v_account_id
    FROM account a
    WHERE a.account_name = p_account_name;

    -- Insert the new loan and get the new ID
    INSERT INTO loan (
        account_id,
        loan_amount,
        loan_type,
        loan_created_date
    ) VALUES (
        v_account_id,
        p_loan_amount,
        p_loan_type::loan_type_enum,
        p_loan_created_date
    )
    RETURNING loan.loan_id INTO v_new_loan_id;

    -- Return the inserted row
    RETURN QUERY
    SELECT
		al.loan_id,
        al.account_name,
        al.loan_amount,
        al.loan_type,
        al.loan_created_date
    FROM
        account_loan_view al
    WHERE
        al.loan_id = v_new_loan_id;
END;
';
-- rollback DROP FUNCTION insert_and_return_loan;

-- changeset ryanbasiltrickett:insert_and_return_transaction_func
CREATE OR REPLACE FUNCTION insert_and_return_transaction(
    p_debit_account_name VARCHAR,
    p_credit_account_name VARCHAR,
    p_transaction_debit_ref VARCHAR,
    p_transaction_credit_ref VARCHAR,
    p_transaction_amount BIGINT,
    p_transaction_date VARCHAR,
    p_transaction_status VARCHAR
)
RETURNS TABLE (
    transaction_id UUID,
    debit_account_name VARCHAR,
    credit_account_name VARCHAR,
    transaction_debit_ref VARCHAR,
    transaction_credit_ref VARCHAR,
    transaction_amount BIGINT,
    transaction_date CHAR(8),
    transaction_status transaction_status_enum
) 
LANGUAGE plpgsql
AS '
DECLARE
    v_credit_account_id UUID;
    v_debit_account_id UUID;
    v_new_transaction_id UUID;
BEGIN
    -- Get the account_id for the debit account name
    SELECT a.account_id INTO v_debit_account_id
    FROM account a
    WHERE a.account_name = p_debit_account_name;

    -- Get the account_id for the credit account name
    SELECT a.account_id INTO v_credit_account_id
    FROM account a
    WHERE a.account_name = p_credit_account_name;

    -- Insert the new transaction and get the new ID
    INSERT INTO transaction (
        debit_account_id,
        credit_account_id,
        transaction_debit_ref,
        transaction_credit_ref,
        transaction_amount,
        transaction_date,
        transaction_status
    ) VALUES (
        v_debit_account_id,
        v_credit_account_id,
        p_transaction_debit_ref,
        p_transaction_credit_ref,
        p_transaction_amount,
        p_transaction_date,
        p_transaction_status::transaction_status_enum
    )
    RETURNING transaction.transaction_id INTO v_new_transaction_id;

    -- Return the inserted row
    RETURN QUERY
    SELECT
        at.transaction_id,
        at.debit_account_name,
        at.credit_account_name,
        at.transaction_debit_ref,
        at.transaction_credit_ref,
        at.transaction_amount,
        at.transaction_date,
        at.transaction_status
    FROM
        account_transaction_view at
    WHERE
        at.transaction_id = v_new_transaction_id;
END;
';
-- rollback DROP FUNCTION insert_and_return_transaction;

-- changeset ryanbasiltrickett:update_and_return_transaction_func
CREATE OR REPLACE FUNCTION update_and_return_transaction(
    p_transaction_id VARCHAR,
    p_debit_account_name VARCHAR,
    p_credit_account_name VARCHAR,
    p_transaction_debit_ref VARCHAR,
    p_transaction_credit_ref VARCHAR,
    p_transaction_amount BIGINT,
    p_transaction_date VARCHAR,
    p_transaction_status VARCHAR
)
RETURNS TABLE (
    transaction_id UUID,
    debit_account_name VARCHAR,
    credit_account_name VARCHAR,
    transaction_debit_ref VARCHAR,
    transaction_credit_ref VARCHAR,
    transaction_amount BIGINT,
    transaction_date CHAR(8),
    transaction_status transaction_status_enum
) 
LANGUAGE plpgsql
AS '
DECLARE
    v_credit_account_id UUID;
    v_debit_account_id UUID;
BEGIN
    -- Get the account_id for the debit account name
    SELECT a.account_id INTO v_debit_account_id
    FROM account a
    WHERE a.account_name = p_debit_account_name;

    -- Get the account_id for the credit account name
    SELECT a.account_id INTO v_credit_account_id
    FROM account a
    WHERE a.account_name = p_credit_account_name;

    -- Update the transaction
    UPDATE transaction t
    SET
        debit_account_id = v_debit_account_id,
        credit_account_id = v_credit_account_id,
        transaction_debit_ref = p_transaction_debit_ref,
        transaction_credit_ref = p_transaction_credit_ref,
        transaction_amount = p_transaction_amount,
        transaction_date = p_transaction_date,
        transaction_status = p_transaction_status::transaction_status_enum
    WHERE
        t.transaction_id = p_transaction_id::UUID;

    -- Return the inserted row
    RETURN QUERY
    SELECT
        at.transaction_id,
        at.debit_account_name,
        at.credit_account_name,
        at.transaction_debit_ref,
        at.transaction_credit_ref,
        at.transaction_amount,
        at.transaction_date,
        at.transaction_status
    FROM
        account_transaction_view at
    WHERE
        at.transaction_id = p_transaction_id::UUID;
END;
';
-- rollback DROP FUNCTION update_and_return_transaction;

-- changeset ryanbasiltrickett:insert_and_return_interbank_transaction_func
CREATE OR REPLACE FUNCTION insert_and_return_interbank_transaction(
    p_transaction_id VARCHAR,
    p_external_account_id VARCHAR,
    p_interbank_transaction_status VARCHAR
)
RETURNS TABLE (
    interbank_transaction_id UUID,
    transaction_id UUID,
    external_account_id VARCHAR,
    interbank_transaction_status interbank_transaction_status_enum
) 
LANGUAGE plpgsql
AS '
DECLARE
    v_new_interbank_transaction_id UUID;
BEGIN
    -- Insert the new interbank transaction and get the new ID
    INSERT INTO interbank_transaction (
        transaction_id,
        external_account_id,
        interbank_transaction_status
    ) VALUES (
        p_transaction_id::uuid,
        p_external_account_id,
        p_interbank_transaction_status::interbank_transaction_status_enum
    )
    RETURNING interbank_transaction.interbank_transaction_id INTO v_new_interbank_transaction_id;

    -- Return the inserted row
    RETURN QUERY
    SELECT
        it.interbank_transaction_id,
        it.transaction_id,
        it.external_account_id,
        it.interbank_transaction_status
    FROM
        interbank_transaction it
    WHERE
        it.interbank_transaction_id = v_new_interbank_transaction_id;
END;
';
-- rollback DROP FUNCTION insert_and_return_interbank_transaction;

-- changeset ryanbasiltrickett:update_and_return_interbank_transaction_func
CREATE OR REPLACE FUNCTION update_and_return_interbank_transaction(
    p_interbank_transaction_id VARCHAR,
    p_transaction_id VARCHAR,
    p_external_account_id VARCHAR,
    p_interbank_transaction_status VARCHAR
)
RETURNS TABLE (
    interbank_transaction_id UUID,
    transaction_id UUID,
    external_account_id VARCHAR,
    interbank_transaction_status VARCHAR
) 
LANGUAGE plpgsql
AS '
BEGIN
    -- Update the interbank transaction
    UPDATE interbank_transaction it
    SET
        transaction_id = p_transaction_id::uuid,
        external_account_id = p_external_account_id,
        interbank_transaction_status = p_interbank_transaction_status
    WHERE
        it.interbank_transaction_id = p_interbank_transaction_id::uuid;

    -- Return the inserted row
    RETURN QUERY
    SELECT
        it.interbank_transaction_id,
        it.transaction_id,
        it.external_account_id,
        it.interbank_transaction_status::interbank_transaction_status_enum
    FROM
        interbank_transaction it
    WHERE
        it.interbank_transaction_id = p_interbank_transaction_id::UUID;
END;
';
-- rollback DROP FUNCTION update_and_return_interbank_transaction;

-- changeset ryanbasiltrickett:insert_and_return_loan_transaction_func
CREATE OR REPLACE FUNCTION insert_and_return_loan_transaction(
    p_loan_id VARCHAR,
    p_transaction_id VARCHAR
)
RETURNS TABLE (
    loan_transaction_id UUID,
    loan_id UUID,
    transaction_id UUID
) 
LANGUAGE plpgsql
AS '
DECLARE
    v_new_loan_transaction_id UUID;
BEGIN
    -- Insert the new loan transaction and get the new ID
    INSERT INTO loan_transaction (
        loan_id,
        transaction_id
    ) VALUES (
        p_loan_id::uuid,
        p_transaction_id
    )
    RETURNING loan_transaction.loan_transaction_id INTO v_new_loan_transaction_id;

    -- Return the inserted row
    RETURN QUERY
    SELECT
        lt.loan_transaction_id,
        lt.loan_id,
        lt.transaction_id
    FROM
        loan_transaction lt
    WHERE
        lt.loan_transaction_id = v_new_loan_transaction_id;
END;
';
-- rollback DROP FUNCTION insert_and_return_loan_transaction;

-- changeset ryanbasiltrickett:insert_and_return_debit_order_transaction_func
CREATE OR REPLACE FUNCTION insert_and_return_debit_order_transaction(
    p_debit_order_id VARCHAR,
    p_transaction_id VARCHAR
)
RETURNS TABLE (
    debit_order_transaction_id UUID,
    debit_order_id UUID,
    transaction_id UUID
) 
LANGUAGE plpgsql
AS '
DECLARE
    v_new_debit_order_transaction_id UUID;
BEGIN
    -- Insert the new debit order transaction and get the new ID
    INSERT INTO debit_order_transaction (
        debit_order_id,
        transaction_id
    ) VALUES (
        p_debit_order_id::uuid,
        p_transaction_id::uuid
    )
    RETURNING debit_order_transaction.debit_order_transaction_id INTO v_new_debit_order_transaction_id;

    -- Return the inserted row
    RETURN QUERY
    SELECT
        dot.debit_order_transaction_id,
        dot.debit_order_id,
        dot.transaction_id
    FROM
        debit_order_transaction dot
    WHERE
        dot.debit_order_transaction_id = v_new_debit_order_transaction_id;
END;
';
-- rollback DROP FUNCTION insert_and_return_debit_order_transaction;