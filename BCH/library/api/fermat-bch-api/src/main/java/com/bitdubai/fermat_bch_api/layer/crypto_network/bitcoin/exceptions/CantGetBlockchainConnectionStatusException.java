package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 1/10/16.
 */
public class CantGetBlockchainConnectionStatusException extends FermatException {

    public final static String DEFAULT_MESSAGE = "There was an error getting the connection status of the blockchain.";

    public CantGetBlockchainConnectionStatusException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
