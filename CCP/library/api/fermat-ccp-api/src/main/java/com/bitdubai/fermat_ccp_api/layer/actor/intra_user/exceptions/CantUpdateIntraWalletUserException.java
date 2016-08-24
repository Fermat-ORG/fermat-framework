package com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by root on 24/08/16.
 */
public class CantUpdateIntraWalletUserException extends FermatException {
    /**
     *
     */
    private static final long serialVersionUID = 7137746546837677675L;

    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE INTRA USER";

    public CantUpdateIntraWalletUserException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateIntraWalletUserException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantUpdateIntraWalletUserException(final String message) {
        this(message, null);
    }

    public CantUpdateIntraWalletUserException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantUpdateIntraWalletUserException() {
        this(DEFAULT_MESSAGE);
    }
}
