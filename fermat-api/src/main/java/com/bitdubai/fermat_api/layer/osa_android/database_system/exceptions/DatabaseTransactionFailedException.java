package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by ciencias on 3/24/15.
 */
public class DatabaseTransactionFailedException extends DatabaseSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 1431539427032863967L;

    public static final String DEFAULT_MESSAGE = "DATABASE TRANSACTION HAS FAILED";

    public DatabaseTransactionFailedException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public DatabaseTransactionFailedException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public DatabaseTransactionFailedException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public DatabaseTransactionFailedException(final String message) {
        this(message, null);
    }

    public DatabaseTransactionFailedException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public DatabaseTransactionFailedException() {
        this(DEFAULT_MESSAGE);
    }
}
