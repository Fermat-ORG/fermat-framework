package org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by root on 06/10/15.
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
