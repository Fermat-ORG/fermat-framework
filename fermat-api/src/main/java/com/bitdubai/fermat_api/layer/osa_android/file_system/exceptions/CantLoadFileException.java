package com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions;

/**
 * Created by ciencias on 22.01.15.
 */
public class CantLoadFileException extends FileSystemException {

    /**
     *
     */
    private static final long serialVersionUID = -3969485077547243542L;


    public static final String DEFAULT_MESSAGE = "CAN'T LOAD FILE";

    public CantLoadFileException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantLoadFileException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantLoadFileException(final String message) {
        this(message, null);
    }

    public CantLoadFileException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantLoadFileException() {
        this(DEFAULT_MESSAGE);
    }

}