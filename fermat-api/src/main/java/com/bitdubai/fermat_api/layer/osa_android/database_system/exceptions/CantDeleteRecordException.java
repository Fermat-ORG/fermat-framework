package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by mati
 */
public class CantDeleteRecordException extends DatabaseSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 1055830576804455604L;

    public static final String DEFAULT_MESSAGE = "CAN'T DELETE RECORD";

    public CantDeleteRecordException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDeleteRecordException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }


    public CantDeleteRecordException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantDeleteRecordException(final String message) {
        this(message, null);
    }

    public CantDeleteRecordException(final Exception exception) {
        this(DEFAULT_MESSAGE, exception);
        setStackTrace(exception.getStackTrace());
    }

    public CantDeleteRecordException() {
        this(DEFAULT_MESSAGE);
    }
}