package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Nerio on 07/09/15.
 */
public class CantCreateNewActorAssetUserException extends DAPException {

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE NEW USER";

    public CantCreateNewActorAssetUserException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateNewActorAssetUserException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateNewActorAssetUserException(final String message) {
        this(message, null);
    }

    public CantCreateNewActorAssetUserException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateNewActorAssetUserException() {
        this(DEFAULT_MESSAGE);
    }
}
