package com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantRequestQuotesException</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/02/2016.
 */
public class CantRequestQuotesException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T REQUEST QUOTES EXCEPTION";

    public CantRequestQuotesException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRequestQuotesException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
