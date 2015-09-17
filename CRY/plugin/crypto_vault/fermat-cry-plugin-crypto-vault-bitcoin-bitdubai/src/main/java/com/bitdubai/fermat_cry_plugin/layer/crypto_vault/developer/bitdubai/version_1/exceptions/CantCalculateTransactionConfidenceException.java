package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 2015.06.24..
 */
public class CantCalculateTransactionConfidenceException extends FermatException {
    public CantCalculateTransactionConfidenceException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
