package com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 2015.06.25..
 */
public class InvalidSendToAddressException extends FermatException {

    private static final String DEFAULT_MESSAGE = "Error trying to send crypto. The destination address is not correct.";

    public InvalidSendToAddressException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public InvalidSendToAddressException(String context, String possibleReason) {
        super(DEFAULT_MESSAGE, null, context, possibleReason);
    }
}
