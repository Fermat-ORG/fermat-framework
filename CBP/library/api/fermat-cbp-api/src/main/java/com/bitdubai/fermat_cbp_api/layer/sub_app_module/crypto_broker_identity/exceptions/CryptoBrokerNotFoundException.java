package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CryptoBrokerNotFoundException</code>
 * is thrown when the crypto broker cannot be found.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/11/2015.
 */
public class CryptoBrokerNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CRYPTO BROKER NOT FOUND EXCEPTION";

    public CryptoBrokerNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CryptoBrokerNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
