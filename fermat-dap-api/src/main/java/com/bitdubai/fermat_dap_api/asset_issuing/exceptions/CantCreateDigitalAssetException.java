package com.bitdubai.fermat_dap_api.asset_issuing.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/09/15.
 */
public class CantCreateDigitalAssetException extends DAPException{

    static final String DEFAULT_MESSAGE = "There was an error creating a Digital Asset.";

    public CantCreateDigitalAssetException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

    public CantCreateDigitalAssetException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }

}
