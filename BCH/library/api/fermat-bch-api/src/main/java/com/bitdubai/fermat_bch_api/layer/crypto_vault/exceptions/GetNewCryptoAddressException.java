package com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 9/22/15.
 */
public class GetNewCryptoAddressException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error trying to get a new Crypto Address.";

    public GetNewCryptoAddressException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
