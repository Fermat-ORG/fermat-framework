package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes.vault_seed.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 2/26/16.
 */
public class InvalidSeedException extends FermatException{
    public static final String DEFAULT_MESSAGE = "The created seed of the vault is not valid!";

    public InvalidSeedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
