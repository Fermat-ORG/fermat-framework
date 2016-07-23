package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by ciencias on 01.02.15.
 */
public class DatabaseNotFoundException extends DatabaseSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 5578619047961290769L;

    public static final String DEFAULT_MESSAGE = "THE DATABASE HAS NOT BEEN FOUND";

    public DatabaseNotFoundException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public DatabaseNotFoundException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public DatabaseNotFoundException(final String message) {
        this(message, null);
    }

    public DatabaseNotFoundException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public DatabaseNotFoundException() {
        this(DEFAULT_MESSAGE);
    }
}
