package com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 1/15/16.
 */
public class CantCreateBitcoinTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error creating a new bitcoin transaction";

    public CantCreateBitcoinTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
