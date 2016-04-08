package com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions;


import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantConfirmConnectionRequestException</code>
 * is thrown when there is an error trying to confirm the connection request.
 * <p>
 * Created by Gabriel Araujo. (31/03/2016)
 */
public class CantConfirmConnectionRequestException extends ARTException {

    private static final String DEFAULT_MESSAGE = "CAN'T CONFIRM CONNECTION REQUEST EXCEPTION";

    public CantConfirmConnectionRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantConfirmConnectionRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
