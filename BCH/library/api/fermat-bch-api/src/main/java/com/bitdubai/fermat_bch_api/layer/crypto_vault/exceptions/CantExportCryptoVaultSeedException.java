package com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/4/16.
 */
public class CantExportCryptoVaultSeedException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error exporting the existing vault seed.";

    public CantExportCryptoVaultSeedException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantExportCryptoVaultSeedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
