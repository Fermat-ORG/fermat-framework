package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by franklin on 15/10/15.
 */
public class CantRequestListActorAssetUserRegisteredException extends DAPException {


    public static final String DEFAULT_MESSAGE = "CAN'T REQUEST LIST ACTOR ASSET USER REGISTERED";


    public CantRequestListActorAssetUserRegisteredException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRequestListActorAssetUserRegisteredException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantRequestListActorAssetUserRegisteredException(final String message) {
        this(message, null);
    }

    public CantRequestListActorAssetUserRegisteredException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantRequestListActorAssetUserRegisteredException() {
        this(DEFAULT_MESSAGE);
    }
}
