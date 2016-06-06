package org.fermat.fermat_dap_api.layer.dap_transaction.user_redemption.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/11/15.
 */
public class CantRedeemDigitalAssetException extends DAPException {
    static final String DEFAULT_MESSAGE = "There was an error Distributing Digital Assets.";

    public CantRedeemDigitalAssetException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantRedeemDigitalAssetException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }

    public CantRedeemDigitalAssetException(Exception exception) {
        super(exception);
    }
}
