package com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/11/15.
 */
public class CantRedeemDigitalAssetException extends DAPException {
    static final String DEFAULT_MESSAGE = "There was an error Distributing Digital Assets.";

    public CantRedeemDigitalAssetException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantRedeemDigitalAssetException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }
}
