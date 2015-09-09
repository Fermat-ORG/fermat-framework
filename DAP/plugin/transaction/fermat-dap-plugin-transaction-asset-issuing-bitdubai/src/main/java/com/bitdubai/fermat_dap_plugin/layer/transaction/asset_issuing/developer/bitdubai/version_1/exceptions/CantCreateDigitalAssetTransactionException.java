package com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/09/15.
 */
public class CantCreateDigitalAssetTransactionException extends DAPException{

    static final String DEFAULT_MESSAGE = "There was an error creating a Digital Asset.";

    public CantCreateDigitalAssetTransactionException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantCreateDigitalAssetTransactionException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }

}
