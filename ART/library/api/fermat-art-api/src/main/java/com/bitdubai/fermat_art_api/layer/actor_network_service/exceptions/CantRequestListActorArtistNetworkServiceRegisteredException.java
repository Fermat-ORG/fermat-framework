package com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by franklin on 15/10/15.
 */
public class CantRequestListActorArtistNetworkServiceRegisteredException extends ARTException {


    public static final String DEFAULT_MESSAGE = "CAN'T REQUEST LIST ACTOR ASSET USER REGISTERED";


    public CantRequestListActorArtistNetworkServiceRegisteredException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRequestListActorArtistNetworkServiceRegisteredException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantRequestListActorArtistNetworkServiceRegisteredException(final String message) {
        this(message, null);
    }

    public CantRequestListActorArtistNetworkServiceRegisteredException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantRequestListActorArtistNetworkServiceRegisteredException() {
        this(DEFAULT_MESSAGE);
    }
}
