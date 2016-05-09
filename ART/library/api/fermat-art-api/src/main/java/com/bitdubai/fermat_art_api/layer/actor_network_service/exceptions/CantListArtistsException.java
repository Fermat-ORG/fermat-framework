package com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_cbp_api.layer.actor_network_service.crypto_broker.exceptions.CantListArtistsException</code>
 * is thrown when there is an error trying to list crypto brokers.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/11/2015.
 */
public class CantListArtistsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T LIST ARTIST EXCEPTION";

    public CantListArtistsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListArtistsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
