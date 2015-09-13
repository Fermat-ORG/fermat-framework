package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Nerio on 07/09/15.
 */
public class CantSingMessageException extends DAPException {

    public static final String DEFAULT_MESSAGE = "CAN'T SIGN MESSAGE ISSUER";

    public CantSingMessageException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
