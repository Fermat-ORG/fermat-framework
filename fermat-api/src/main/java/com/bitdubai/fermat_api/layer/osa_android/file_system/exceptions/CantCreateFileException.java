package com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions;

/**
 * Created by toshiba on 08/04/2015.
 */
public class CantCreateFileException extends FileSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 2411843361344674579L;

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE FILE";

    public CantCreateFileException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateFileException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantCreateFileException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateFileException(final String message) {
        this(message, null);
    }

    public CantCreateFileException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateFileException() {
        this(DEFAULT_MESSAGE);
    }

}
