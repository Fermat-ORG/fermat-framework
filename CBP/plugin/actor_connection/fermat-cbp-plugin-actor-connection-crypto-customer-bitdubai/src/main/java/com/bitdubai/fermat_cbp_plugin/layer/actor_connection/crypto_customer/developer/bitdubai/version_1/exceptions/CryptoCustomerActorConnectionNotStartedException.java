package com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_plugin.layer.actor_connection.crypto_customer.developer.bitdubai.version_1.exceptions.CryptoCustomerActorConnectionNotStartedException</code>
 * is thrown when we're trying to do something in the plug-in but this one is not started.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/02/2015.
 */
public class CryptoCustomerActorConnectionNotStartedException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CRYPTO CUSTOMER ACTOR CONNECTION NOT STARTED EXCEPTION";

    public CryptoCustomerActorConnectionNotStartedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CryptoCustomerActorConnectionNotStartedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CryptoCustomerActorConnectionNotStartedException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
