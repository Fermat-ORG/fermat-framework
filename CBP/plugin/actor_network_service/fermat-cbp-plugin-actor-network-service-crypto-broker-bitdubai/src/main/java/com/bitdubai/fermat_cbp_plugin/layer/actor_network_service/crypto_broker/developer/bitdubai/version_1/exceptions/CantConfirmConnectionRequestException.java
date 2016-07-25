package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantConfirmConnectionRequestException</code>
 * is thrown when there is an error trying to confirm the connection request.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/12/2015.
 */
public class CantConfirmConnectionRequestException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CONFIRM CONNECTION REQUEST EXCEPTION";

    public CantConfirmConnectionRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantConfirmConnectionRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
