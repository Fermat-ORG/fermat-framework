package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantValidateConnectionStateException</code>
 * is thrown when there is an error trying to validate the connection state.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 */
public class CantValidateConnectionStateException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T VALIDATE CONNECTION STATE EXCEPTION";

    public CantValidateConnectionStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantValidateConnectionStateException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
