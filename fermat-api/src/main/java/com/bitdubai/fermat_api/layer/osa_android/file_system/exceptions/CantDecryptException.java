package com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions;

/**
 * Created by ciencias on 3/20/15.
 */
public class CantDecryptException extends FileSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 4995274077891666577L;

    public static final String DEFAULT_MESSAGE = "CAN'T DECRYPT FILE";

    public CantDecryptException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDecryptException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantDecryptException(final String message) {
        this(message, null);
    }

    public CantDecryptException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantDecryptException() {
        this(DEFAULT_MESSAGE);
    }
}
