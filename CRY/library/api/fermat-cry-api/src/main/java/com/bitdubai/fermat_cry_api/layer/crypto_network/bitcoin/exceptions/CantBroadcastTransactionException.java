package com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 10/29/15.
 */
public class CantBroadcastTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was a problem broadcasting a trasnaction into the network.";
    public CantBroadcastTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
