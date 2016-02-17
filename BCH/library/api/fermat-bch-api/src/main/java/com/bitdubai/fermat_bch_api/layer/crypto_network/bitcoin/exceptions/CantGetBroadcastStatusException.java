package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 12/31/15.
 */
public class CantGetBroadcastStatusException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error trying to get the Broadcast status of the transction.";

    public CantGetBroadcastStatusException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
