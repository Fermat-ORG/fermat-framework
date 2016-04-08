package com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions;


import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantChangeProtocolStateException</code>
 * is thrown when there is an error trying to change the protocol state.
 * <p>
 * Created by Gabriel Araujo. (31/03/2016)
 */
public class CantChangeProtocolStateException extends ARTException {

    private static final String DEFAULT_MESSAGE = "CAN'T CHANGE PROTOCOL STATE EXCEPTION";

    public CantChangeProtocolStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantChangeProtocolStateException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
