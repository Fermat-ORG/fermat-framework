package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Nerio on 07/09/15.
 */
public class CantCreateActorAssetIssuerException extends DAPException {

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE NEW ISSUER";

    public CantCreateActorAssetIssuerException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateActorAssetIssuerException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateActorAssetIssuerException(final String message) {
        this(message, null);
    }

    public CantCreateActorAssetIssuerException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateActorAssetIssuerException() {
        this(DEFAULT_MESSAGE);
    }
}
