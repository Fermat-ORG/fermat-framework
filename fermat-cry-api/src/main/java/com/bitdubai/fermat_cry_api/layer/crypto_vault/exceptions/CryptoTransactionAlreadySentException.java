package com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 2015.07.10..
 */
public class CryptoTransactionAlreadySentException extends FermatException{
    public CryptoTransactionAlreadySentException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
