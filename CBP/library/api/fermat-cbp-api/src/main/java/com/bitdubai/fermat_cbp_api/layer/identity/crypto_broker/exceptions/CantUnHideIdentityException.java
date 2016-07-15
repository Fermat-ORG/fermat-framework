package com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Miguel Payarez (miguel_payarez@hotmail.com) on 7/11/16.
 */
public class CantUnHideIdentityException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CANT UNHIDE IDENTITY EXCEPTION";

    public CantUnHideIdentityException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUnHideIdentityException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
