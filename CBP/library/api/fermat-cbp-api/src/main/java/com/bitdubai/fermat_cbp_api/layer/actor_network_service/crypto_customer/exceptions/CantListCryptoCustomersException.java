package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_customer.exceptions.CantListCryptoCustomersException</code>
 * is thrown when there is an error trying to list crypto customers.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/11/2015.
 */
public class CantListCryptoCustomersException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T LIST CRYPTO CUSTOMERS EXCEPTION";

    public CantListCryptoCustomersException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListCryptoCustomersException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
