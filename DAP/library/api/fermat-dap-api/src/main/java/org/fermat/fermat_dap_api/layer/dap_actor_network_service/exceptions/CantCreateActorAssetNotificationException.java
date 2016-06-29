package org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by loui on 22/02/15.
 */
public class CantCreateActorAssetNotificationException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE ACTOR ASSET NOTIFICATION";

    public CantCreateActorAssetNotificationException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateActorAssetNotificationException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateActorAssetNotificationException(final String message) {
        this(message, null);
    }

    public CantCreateActorAssetNotificationException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateActorAssetNotificationException() {
        this(DEFAULT_MESSAGE);
    }
}
