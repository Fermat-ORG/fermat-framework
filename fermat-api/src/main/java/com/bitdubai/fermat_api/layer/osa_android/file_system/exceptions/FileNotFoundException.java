package com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions;

/**
 * Created by ciencias on 22.01.15.
 */
public class FileNotFoundException extends FileSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 4973291923823272413L;

    public static final String DEFAULT_MESSAGE = "FILE NOT FOUND";

    public FileNotFoundException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public FileNotFoundException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public FileNotFoundException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public FileNotFoundException(final String message) {
        this(message, null);
    }

    public FileNotFoundException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public FileNotFoundException() {
        this(DEFAULT_MESSAGE);
    }

}
