package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 9/30/15.
 */
public class CantMonitorBitcoinNetworkException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error trying to monitor the Bitcoin network";

    public CantMonitorBitcoinNetworkException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
