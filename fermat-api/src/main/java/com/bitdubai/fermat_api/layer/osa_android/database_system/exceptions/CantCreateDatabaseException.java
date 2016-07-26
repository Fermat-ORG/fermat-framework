package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by toshiba on 23/03/2015.
 */
public class CantCreateDatabaseException extends DatabaseSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 4831581407768860533L;

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE THE DATABASE";

    public CantCreateDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateDatabaseException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantCreateDatabaseException(final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantCreateDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateDatabaseException(final String message) {
        this(message, null, null, null);
    }

    public CantCreateDatabaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
