package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/10/15.
 */
public class CantReceiveDigitalAssetException extends DAPException {
    static final String DEFAULT_MESSAGE = "There was an error Receiving Digital Asset to asset wallet.";

    public CantReceiveDigitalAssetException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantReceiveDigitalAssetException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }
}
