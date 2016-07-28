package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantChangeProtocolStateException</code>
 * is thrown when there is an error trying to change the protocol state.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/12/2015.
 */
public class CantChangeProtocolStateException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T CHANGE PROTOCOL STATE EXCEPTION";

    public CantChangeProtocolStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantChangeProtocolStateException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
