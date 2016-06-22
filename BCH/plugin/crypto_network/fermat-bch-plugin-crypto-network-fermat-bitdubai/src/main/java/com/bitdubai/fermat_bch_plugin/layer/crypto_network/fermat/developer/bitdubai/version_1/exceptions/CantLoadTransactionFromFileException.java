package com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 1/30/16.
 */
public class CantLoadTransactionFromFileException extends FermatException {
    public final static String DEFAULT_MESSAGE = "There was an error loading a transaction from file";

    public CantLoadTransactionFromFileException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
