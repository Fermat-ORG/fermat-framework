package com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 1/2/16.
 */
public class CouldNotGenerateTransactionException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error generating the Bitcoin transaction";

    public CouldNotGenerateTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
