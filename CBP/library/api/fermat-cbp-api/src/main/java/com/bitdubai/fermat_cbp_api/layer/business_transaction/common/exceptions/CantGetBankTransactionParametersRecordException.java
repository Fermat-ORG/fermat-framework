package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CBPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 15/02/16.
 */
public class CantGetBankTransactionParametersRecordException extends CBPException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET A BANK TRANSACTION PARAMETERS RECORD";

    public CantGetBankTransactionParametersRecordException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetBankTransactionParametersRecordException(
            final Exception exception,
            final String context,
            final String possibleReason) {
        super(DEFAULT_MESSAGE, exception, context, possibleReason);
    }

    public CantGetBankTransactionParametersRecordException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetBankTransactionParametersRecordException(
            final String message) {
        this(message, null);
    }

    public CantGetBankTransactionParametersRecordException(
            final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetBankTransactionParametersRecordException() {
        this(DEFAULT_MESSAGE);
    }
}
