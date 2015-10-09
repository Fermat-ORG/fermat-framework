package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 18/09/15.
 */
public class CantGetDigitalAssetFromLocalStorageException extends DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error getting a Digital Asset from local storage.";

    public CantGetDigitalAssetFromLocalStorageException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
