package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 1/2/16.
 */
public class CantStoreBitcoinTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error storing the bitcoin transaction on the Crypto Network.";

    public CantStoreBitcoinTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
