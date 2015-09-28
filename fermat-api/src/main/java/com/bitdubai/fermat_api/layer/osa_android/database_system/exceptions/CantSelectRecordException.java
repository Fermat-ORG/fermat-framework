package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by natalia on 07/07/15.
 */
public class CantSelectRecordException extends DatabaseSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 9099890596007453950L;

    public static final String DEFAULT_MESSAGE = "CAN'T SELECT RECORDS";

    public CantSelectRecordException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSelectRecordException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantSelectRecordException(final String message) {
        this(message, null);
    }

    public CantSelectRecordException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantSelectRecordException() {
        this(DEFAULT_MESSAGE);
    }
}
