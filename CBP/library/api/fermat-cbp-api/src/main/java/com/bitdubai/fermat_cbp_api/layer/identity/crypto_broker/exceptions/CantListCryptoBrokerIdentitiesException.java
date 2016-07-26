package com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 10.09.15.
 */

public class CantListCryptoBrokerIdentitiesException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Falled To Get Crypto Broker Identity.";

    public CantListCryptoBrokerIdentitiesException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
