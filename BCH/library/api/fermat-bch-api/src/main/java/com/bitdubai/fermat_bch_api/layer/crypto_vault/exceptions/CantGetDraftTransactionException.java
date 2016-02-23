package com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 2/22/16.
 */
public class CantGetDraftTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error getting a stored draft transaction.";

    public CantGetDraftTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
