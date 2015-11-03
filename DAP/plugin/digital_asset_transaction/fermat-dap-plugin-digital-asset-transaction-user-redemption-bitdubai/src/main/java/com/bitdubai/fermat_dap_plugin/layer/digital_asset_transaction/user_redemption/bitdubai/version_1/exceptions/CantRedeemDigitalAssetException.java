package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 02/11/15.
 */
public class CantRedeemDigitalAssetException extends DAPException {
    static final String DEFAULT_MESSAGE = "There was an error Redeeming Digital Asset to an external actor.";

    public CantRedeemDigitalAssetException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantRedeemDigitalAssetException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }
}
