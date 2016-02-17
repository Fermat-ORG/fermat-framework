package com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CBPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/02/16.
 */
public class CantGetCashTransactionParameterException extends CBPException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET A BANK TRANSACTION PARAMETERS RECORD";

    public CantGetCashTransactionParameterException(
            final String message,
            final Exception cause,
            final String context,
            final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetCashTransactionParameterException(
            final Exception exception,
            final String context,
            final String possibleReason) {
        super(DEFAULT_MESSAGE, exception, context, possibleReason);
    }

    public CantGetCashTransactionParameterException(
            final String message,
            final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetCashTransactionParameterException(
            final String message) {
        this(message, null);
    }

    public CantGetCashTransactionParameterException(
            final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantGetCashTransactionParameterException() {
        this(DEFAULT_MESSAGE);
    }
}
