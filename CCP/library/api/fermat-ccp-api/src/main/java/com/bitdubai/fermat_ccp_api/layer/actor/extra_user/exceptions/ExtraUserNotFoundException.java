package com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>IntraUserNotFoundException</code>
 * is thrown when we can't find an extra user
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ExtraUserNotFoundException extends FermatException {

    public static final String DEFAULT_MESSAGE = "EXTRA USER NOT FOUND EXCEPTION";

    public ExtraUserNotFoundException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ExtraUserNotFoundException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public ExtraUserNotFoundException(final String message) {
        this(message, null);
    }

    public ExtraUserNotFoundException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public ExtraUserNotFoundException() {
        this(DEFAULT_MESSAGE);
    }

}
