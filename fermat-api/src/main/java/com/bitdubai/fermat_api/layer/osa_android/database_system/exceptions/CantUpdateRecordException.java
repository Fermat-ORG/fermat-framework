package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by toshiba on 24/03/2015.
 */
public class CantUpdateRecordException extends DatabaseSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 9099890596007453950L;

    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE RECORD";

    public CantUpdateRecordException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateRecordException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantUpdateRecordException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantUpdateRecordException(final String message) {
        this(message, null);
    }

    public CantUpdateRecordException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantUpdateRecordException() {
        this(DEFAULT_MESSAGE);
    }
}
