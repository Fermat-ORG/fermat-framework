package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by eze on 11/06/15.
 */
public class ExpectedTransactionNotFoundException extends FermatException {

    public static final String DEFAULT_MESSAGE = "EXPECTED TRANSACTION NOT FOUND";

    public ExpectedTransactionNotFoundException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ExpectedTransactionNotFoundException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public ExpectedTransactionNotFoundException(final String message) {
        this(message, null);
    }

    public ExpectedTransactionNotFoundException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public ExpectedTransactionNotFoundException() {
        this(DEFAULT_MESSAGE);
    }
}
