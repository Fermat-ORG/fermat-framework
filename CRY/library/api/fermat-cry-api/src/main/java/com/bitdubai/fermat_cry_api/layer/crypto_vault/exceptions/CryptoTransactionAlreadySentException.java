package com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 2015.07.10..
 */
public class CryptoTransactionAlreadySentException extends FermatException{

    private static final String DEFAULT_MESSAGE = "\"This transaction has already been sent before.\"";

    public CryptoTransactionAlreadySentException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CryptoTransactionAlreadySentException(String context, String possibleReason) {
        super(DEFAULT_MESSAGE, null, context, possibleReason);
    }
}
