package com.bitdubai.fermat_api.layer.dmp_transaction;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by loui on 23/02/15.
 */
public class TransactionServiceNotStartedException extends FermatException {

    /**
     *
     */
    private static final long serialVersionUID = 5294190635262257119L;

    public static final String DEFAULT_MESSAGE = "TRANSACTION SERVICE NOT STARTED";

    public TransactionServiceNotStartedException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public TransactionServiceNotStartedException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public TransactionServiceNotStartedException(final String message) {
        this(message, null);
    }

    public TransactionServiceNotStartedException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public TransactionServiceNotStartedException() {
        this(DEFAULT_MESSAGE);
    }
}
