package com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.exceptions.CantHideIdentityException</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/02/2016.
 */
public class CantHideIdentityException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T HIDE IDENTITY EXCEPTION";

    public CantHideIdentityException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHideIdentityException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
