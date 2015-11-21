package com.bitdubai.fermat_cbp_api.layer.network_service.exceptions;

import com.bitdubai.fermat_api.layer.CBPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/11/15.
 */
public class CantSendBusinessTransactionHashException extends CBPException {

    public static final String DEFAULT_MESSAGE = "CAN'T SEND BUSINESS TRANSACTION HASH";

    public CantSendBusinessTransactionHashException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSendBusinessTransactionHashException(final Exception exception, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, exception, context, possibleReason);
    }

    public CantSendBusinessTransactionHashException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSendBusinessTransactionHashException(final String message) {
        this(message, null);
    }

    public CantSendBusinessTransactionHashException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantSendBusinessTransactionHashException() {
        this(DEFAULT_MESSAGE);
    }
}
