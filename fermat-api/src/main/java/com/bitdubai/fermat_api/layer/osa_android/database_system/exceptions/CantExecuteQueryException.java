package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Exception thrown when you can't execute a query.
 * Created by Leon Acosta (laion.cj91@gmail.com) on 28/08/2015.
 */
public class CantExecuteQueryException extends DatabaseSystemException {

    private static final long serialVersionUID = 4831581407768860533L;

    public static final String DEFAULT_MESSAGE = "CAN'T EXECUTE QUERY";

    public CantExecuteQueryException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantExecuteQueryException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantExecuteQueryException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantExecuteQueryException(final String message) {
        this(message, null);
    }

    public CantExecuteQueryException(final Exception exception) {
        this(DEFAULT_MESSAGE, exception);
        setStackTrace(exception.getStackTrace());
    }

    public CantExecuteQueryException() {
        this(DEFAULT_MESSAGE);
    }
}
