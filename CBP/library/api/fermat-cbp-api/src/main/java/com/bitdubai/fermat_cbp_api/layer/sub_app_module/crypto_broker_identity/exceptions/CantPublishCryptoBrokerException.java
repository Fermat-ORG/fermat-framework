package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by eze on 2015.07.30..
 */
public class CantPublishCryptoBrokerException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T PUBLISH CRYPTO BROKER IDENTITY EXCEPTION";

    public CantPublishCryptoBrokerException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantPublishCryptoBrokerException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
