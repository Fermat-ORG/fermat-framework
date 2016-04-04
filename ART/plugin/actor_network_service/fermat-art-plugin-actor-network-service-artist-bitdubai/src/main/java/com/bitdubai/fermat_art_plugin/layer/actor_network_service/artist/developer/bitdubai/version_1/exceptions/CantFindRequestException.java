package com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions;


import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_broker.developer.bitdubai.version_1.exceptions.CantFindRequestException</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p/>
 * Created by Gabriel Araujo. (31/03/2016)
 */
public class CantFindRequestException extends ARTException {

    private static final String DEFAULT_MESSAGE = "CAN'T FIND REQUEST EXCEPTION";

    public CantFindRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantFindRequestException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
