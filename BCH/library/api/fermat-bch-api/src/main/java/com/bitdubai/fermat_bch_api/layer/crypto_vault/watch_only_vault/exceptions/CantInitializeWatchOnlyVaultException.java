package com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 12/30/15.
 */
public class CantInitializeWatchOnlyVaultException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error initializing the Watch Only Vault";

    public CantInitializeWatchOnlyVaultException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
