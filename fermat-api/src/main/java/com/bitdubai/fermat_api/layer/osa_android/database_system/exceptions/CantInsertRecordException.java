package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by toshiba on 24/03/2015.
 */
public class CantInsertRecordException extends DatabaseSystemException {

    /**
     *
     */
    private static final long serialVersionUID = -327709783649435604L;

    public static final String DEFAULT_MESSAGE = "CAN'T INSERT RECORD";

    public CantInsertRecordException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInsertRecordException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantInsertRecordException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInsertRecordException(final String message) {
        this(message, null);
    }

    public CantInsertRecordException(final Exception exception) {
        this(DEFAULT_MESSAGE, exception);
        setStackTrace(exception.getStackTrace());
    }

    public CantInsertRecordException() {
        this(DEFAULT_MESSAGE);
    }
}
