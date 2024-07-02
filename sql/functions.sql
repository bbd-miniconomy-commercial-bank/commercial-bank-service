-- liquibase formatted sql

-- changeset ryanbasiltrickett:update_debit_order_by_account_names_func
CREATE OR REPLACE FUNCTION update_and_return_debit_order(
    p_debit_order_id UUID,
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
DECLARE
    v_credit_account_id UUID;
    v_debit_account_id UUID;
BEGIN
    -- Get the account_id for the credit account name
    SELECT account_id INTO v_credit_account_id
    FROM account
    WHERE account_name = p_credit_account_name;

    -- Get the account_id for the debit account name
    SELECT account_id INTO v_debit_account_id
    FROM account
    WHERE account_name = p_debit_account_name;

    -- Update the debit order
    UPDATE debit_order
    SET
        credit_account_id = v_credit_account_id,
        debit_order_debit_ref = p_debit_order_debit_ref,
        debit_order_credit_ref = p_debit_order_credit_ref,
        debit_order_amount = p_debit_order_amount,
        debit_order_created_date = p_debit_order_created_date,
        debit_order_disabled = p_debit_order_disabled
    WHERE
        debit_order_id = p_debit_order_id;

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
        ado.debit_order_id = v_new_debit_order_id;
END;
';
-- rollback DROP PROCEDURE IF EXISTS update_and_return_debit_order;

-- changeset ryanbasiltrickett:insert_and_return_debit_order_func
CREATE OR REPLACE FUNCTION insert_and_return_debit_order(
    p_debit_account_name VARCHAR,
    p_credit_account_name VARCHAR,
    p_debit_order_debit_ref VARCHAR,
    p_debit_order_credit_ref VARCHAR,
    p_debit_order_amount BIGINT,
    p_debit_order_created_date CHAR(8),
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
    v_credit_account_id UUID;
    v_debit_account_id UUID;
    v_new_debit_order_id UUID;
BEGIN
    -- Get the account_id for the credit account name
    SELECT account_id INTO v_credit_account_id
    FROM account
    WHERE account_name = p_credit_account_name;

    -- Get the account_id for the debit account name
    SELECT account_id INTO v_debit_account_id
    FROM account
    WHERE account_name = p_debit_account_name;

    -- Insert the new debit order and get the new ID
    INSERT INTO debit_order (
        credit_account_id,
        debit_account_id,
        debit_order_debit_ref,
        debit_order_credit_ref,
        debit_order_amount,
        debit_order_created_date,
        debit_order_disabled
    ) VALUES (
        v_credit_account_id,
        v_debit_account_id,
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
-- rollback DROP PROCEDURE IF EXISTS insert_and_return_debit_order;