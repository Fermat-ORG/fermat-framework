package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by eze on 09/06/15.
 */
public class CantConfirmTransactionException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T CONFIRM TRANSACTION";

    public CantConfirmTransactionException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantConfirmTransactionException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantConfirmTransactionException(final String message) {
        this(message, null);
    }

    public CantConfirmTransactionException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantConfirmTransactionException() {
        this(DEFAULT_MESSAGE);
    }
}
