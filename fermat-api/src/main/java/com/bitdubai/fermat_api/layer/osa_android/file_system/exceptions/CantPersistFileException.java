package com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions;

/**
 * Created by ciencias on 22.01.15.
 */
public class CantPersistFileException extends FileSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 4841032427648911456L;

    public static final String DEFAULT_MESSAGE = "CAN'T PERSIST FILE";

    public CantPersistFileException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantPersistFileException(final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantPersistFileException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantPersistFileException(final String message) {
        this(message, null, null, null);
    }

    public CantPersistFileException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantPersistFileException() {
        this(DEFAULT_MESSAGE);
    }

}
