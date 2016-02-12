package com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantHideIdentityException</code>
 * is thrown when there is an error trying to hiden an identity.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/11/2015.
 */
public class CantUpdateCustomerIdentityException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CANT UPDATE CUSTOMER IDENTITY EXCEPTION";

    public CantUpdateCustomerIdentityException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateCustomerIdentityException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
