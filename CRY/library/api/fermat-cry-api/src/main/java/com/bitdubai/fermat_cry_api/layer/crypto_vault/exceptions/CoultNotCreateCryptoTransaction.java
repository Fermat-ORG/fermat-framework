package com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 9/22/15.
 */
public class CoultNotCreateCryptoTransaction extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was a problem creating a crypto transaction.";

    public CoultNotCreateCryptoTransaction(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
