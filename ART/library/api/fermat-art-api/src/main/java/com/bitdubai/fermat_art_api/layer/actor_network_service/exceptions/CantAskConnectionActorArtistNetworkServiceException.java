package com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by franklin on 15/10/15.
 */
public class CantAskConnectionActorArtistNetworkServiceException extends ARTException {


    public static final String DEFAULT_MESSAGE = "CAN'T REGISTER NEW USER";

    public CantAskConnectionActorArtistNetworkServiceException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantAskConnectionActorArtistNetworkServiceException( final Exception cause,final String message,final String possiblecause) {
        this(message, cause, possiblecause, "");
    }
    public CantAskConnectionActorArtistNetworkServiceException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantAskConnectionActorArtistNetworkServiceException(final String message) {
        this(message, null);
    }

    public CantAskConnectionActorArtistNetworkServiceException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantAskConnectionActorArtistNetworkServiceException() {
        this(DEFAULT_MESSAGE);
    }
}
