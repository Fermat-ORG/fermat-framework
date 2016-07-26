package com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions;


/**
 * Created by ciencias on 3/20/15.
 */
public class CantEncryptException extends FileSystemException {

    /**
     *
     */
    private static final long serialVersionUID = -588222201686370980L;

    public static final String DEFAULT_MESSAGE = "CANT'T ENCRYPT FILE";

    public CantEncryptException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantEncryptException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantEncryptException(final String message) {
        this(message, null);
    }

    public CantEncryptException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantEncryptException() {
        this(DEFAULT_MESSAGE);
    }
}
