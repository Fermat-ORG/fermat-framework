package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by toshiba on 24/03/2015.
 */
public class CantOpenDatabaseException extends DatabaseSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 4736497835462025622L;

    public static final String DEFAULT_MESSAGE = "CAN'T OPEN THE DATABASE";

    public CantOpenDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantOpenDatabaseException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantOpenDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantOpenDatabaseException(final String message) {
        this(message, null);
    }

    public CantOpenDatabaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantOpenDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
