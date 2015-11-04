package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 9/29/15.
 */
public class CantGetGenesisTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "Cant get Genesis transaction from asset vault.";

    public CantGetGenesisTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
