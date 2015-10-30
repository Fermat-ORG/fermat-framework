package com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 2015.06.25..
 */
public class CouldNotSendMoneyException extends FermatException {
    public CouldNotSendMoneyException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
