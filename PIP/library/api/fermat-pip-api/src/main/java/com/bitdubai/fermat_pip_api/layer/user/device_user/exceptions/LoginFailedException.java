package com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by ciencias on 22.01.15.
 */
public class LoginFailedException extends FermatException {

    /**
     *
     */
    private static final long serialVersionUID = 4692401032092379086L;

    public static final String DEFAULT_MESSAGE = "LOGIN FAILED";

    public LoginFailedException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public LoginFailedException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public LoginFailedException(final String message) {
        this(message, null);
    }

    public LoginFailedException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public LoginFailedException() {
        this(DEFAULT_MESSAGE);
    }
}
