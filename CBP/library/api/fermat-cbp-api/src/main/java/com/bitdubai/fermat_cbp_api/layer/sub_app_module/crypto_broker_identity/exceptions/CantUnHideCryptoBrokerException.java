package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Miguel Payarez (miguel_payarez@hotmail.com) on 7/11/16.
 */
public class CantUnHideCryptoBrokerException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T UNHIDE CRYPTO BROKER IDENTITY EXCEPTION";

    public CantUnHideCryptoBrokerException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUnHideCryptoBrokerException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}

