package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 2015.06.24..
 * Modified by lnacosta (laion.cj91@gmail.com) on 15/10/2015.
 */
public class CantCalculateTransactionConfidenceException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T CALCULATE TRANSACTION CONFIDENCE EXCEPTION";

    public CantCalculateTransactionConfidenceException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantCalculateTransactionConfidenceException(String context, String possibleReason) {
        super(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantCalculateTransactionConfidenceException() {
        super(DEFAULT_MESSAGE, null, null, null);
    }

}
