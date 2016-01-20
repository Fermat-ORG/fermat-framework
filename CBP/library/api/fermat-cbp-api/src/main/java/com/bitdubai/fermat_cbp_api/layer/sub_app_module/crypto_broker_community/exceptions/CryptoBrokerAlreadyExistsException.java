package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerAlreadyExistsException</code>
 * is thrown when we're trying to create a crypto broker, but the same already exists.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/12/2015.
 */
public class CryptoBrokerAlreadyExistsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CRYPTO BROKER ALREADY EXISTS EXCEPTION";

    public CryptoBrokerAlreadyExistsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CryptoBrokerAlreadyExistsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
