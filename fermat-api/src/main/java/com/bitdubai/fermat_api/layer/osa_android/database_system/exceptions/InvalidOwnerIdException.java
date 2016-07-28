package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

/**
 * Created by ciencias on 3/23/15.
 */
public class InvalidOwnerIdException extends DatabaseSystemException {

    /**
     *
     */
    private static final long serialVersionUID = 8223773873444754668L;

    public static final String DEFAULT_MESSAGE = "INVALID OWNER ID";

    public InvalidOwnerIdException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public InvalidOwnerIdException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public InvalidOwnerIdException(final String message) {
        this(message, null);
    }

    public InvalidOwnerIdException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public InvalidOwnerIdException() {
        this(DEFAULT_MESSAGE);
    }
}
