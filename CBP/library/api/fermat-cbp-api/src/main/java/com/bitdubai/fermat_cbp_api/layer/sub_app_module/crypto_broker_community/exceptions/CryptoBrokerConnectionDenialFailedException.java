package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerConnectionDenialFailedException</code>
 * is thrown when there is an error trying to deny a crypto broker connection request.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 */
public class CryptoBrokerConnectionDenialFailedException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CRYPTO BROKER CONNECTION DENIAL FAILED EXCEPTION";

    public CryptoBrokerConnectionDenialFailedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CryptoBrokerConnectionDenialFailedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
