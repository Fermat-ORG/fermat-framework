package com.bitdubai.fermat_bch_api.layer.crypto_vault.vault_seed.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 9/19/15.
 */
public class CantLoadExistingVaultSeed extends FermatException {
    public final static String DEFAULT_MESSAGE = "There was an error trying to load an existing Asset Vault Seed.";

    public CantLoadExistingVaultSeed(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
