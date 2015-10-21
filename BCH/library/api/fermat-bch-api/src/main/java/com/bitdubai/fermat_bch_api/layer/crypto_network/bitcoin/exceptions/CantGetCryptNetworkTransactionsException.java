package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 10/21/15.
 */
public class CantGetCryptNetworkTransactionsException extends FermatException {
    public CantGetCryptNetworkTransactionsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
