package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by toshiba on 24/03/2015.
 */
public class CantLoadTableToMemoryException extends DatabaseSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 4735647755255875137L;

    public static final String DEFAULT_MESSAGE = "CAN'T LOAD TABLE TO MEMORY";

    public CantLoadTableToMemoryException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantLoadTableToMemoryException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantLoadTableToMemoryException(final String message) {
        this(message, null);
    }

    public CantLoadTableToMemoryException(final Exception exception) {
        this(DEFAULT_MESSAGE, exception);
        setStackTrace(exception.getStackTrace());
    }

    public CantLoadTableToMemoryException() {
        this(DEFAULT_MESSAGE);
    }
}
