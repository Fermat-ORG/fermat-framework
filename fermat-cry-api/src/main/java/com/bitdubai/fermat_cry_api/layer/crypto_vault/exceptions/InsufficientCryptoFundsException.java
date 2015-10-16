package com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cry_api.CryptoException;

/**
 * Created by rodrigo on 2015.06.19..
 */
public class InsufficientCryptoFundsException extends FermatException {
    public InsufficientCryptoFundsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
