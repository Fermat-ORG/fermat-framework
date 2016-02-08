package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantConfirmQuotesRequestException</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/02/2016.
 */
public class CantConfirmQuotesRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CONFIRM QUOTES REQUEST EXCEPTION";

    public CantConfirmQuotesRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantConfirmQuotesRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
