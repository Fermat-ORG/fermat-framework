package org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions;

/**
 * Created by Nerio on 07/09/15.
 */
public class CantSingMessageException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {

    public static final String DEFAULT_MESSAGE = "CAN'T SIGN MESSAGE ISSUER";

    public CantSingMessageException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
