package com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 2/10/16.
 */
public class CantCreateDraftTransactionException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error creating a draft bitcoin transaction";

    public CantCreateDraftTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
