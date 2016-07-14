package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/13/16.
 */
public class CantGetImportedAddressesException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error getting the list of imported addresses";

    public CantGetImportedAddressesException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantGetImportedAddressesException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
