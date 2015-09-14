package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Nerio on 07/09/15.
 */
public class CantCreateNewActorAssetIssuerException extends DAPException {

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE NEW ISSUER";

    public CantCreateNewActorAssetIssuerException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateNewActorAssetIssuerException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateNewActorAssetIssuerException(final String message) {
        this(message, null);
    }

    public CantCreateNewActorAssetIssuerException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateNewActorAssetIssuerException() {
        this(DEFAULT_MESSAGE);
    }
}
