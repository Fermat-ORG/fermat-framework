package com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions;

/**
 * Created by toshiba on 13/02/2015.
 */
public class WrongOwnerIdException extends FileSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 7703342359015571253L;

    public static final String DEFAULT_MESSAGE = "WRONG OWNER ID";

    public WrongOwnerIdException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public WrongOwnerIdException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public WrongOwnerIdException(final String message) {
        this(message, null);
    }

    public WrongOwnerIdException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public WrongOwnerIdException() {
        this(DEFAULT_MESSAGE);
    }
}
