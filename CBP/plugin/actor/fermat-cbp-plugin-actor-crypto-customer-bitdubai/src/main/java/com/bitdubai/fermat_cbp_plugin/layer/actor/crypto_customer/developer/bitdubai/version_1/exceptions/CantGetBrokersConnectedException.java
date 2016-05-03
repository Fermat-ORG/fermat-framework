package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Angel 15/02/2015
 */
public class CantGetBrokersConnectedException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CANT GET BROKERS CONNECTED EXCEPTION";

    public CantGetBrokersConnectedException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetBrokersConnectedException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}