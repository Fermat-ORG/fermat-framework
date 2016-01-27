package com.bitdubai.fermat_pip_api.layer.module.android_core.exception;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 26/01/16.
 */
public class CantGetBitcoinNetworkStatusException extends FermatException {

    public final static String DEFAULT_MESSAGE = "There was an error getting the connection status of the Bitcoin Network.";

    public CantGetBitcoinNetworkStatusException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
