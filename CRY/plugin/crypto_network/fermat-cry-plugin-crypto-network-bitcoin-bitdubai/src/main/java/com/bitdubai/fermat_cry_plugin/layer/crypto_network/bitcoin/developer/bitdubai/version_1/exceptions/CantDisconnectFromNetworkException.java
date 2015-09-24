package com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 2015.06.29..
 */
public class CantDisconnectFromNetworkException extends FermatException {
    public CantDisconnectFromNetworkException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
