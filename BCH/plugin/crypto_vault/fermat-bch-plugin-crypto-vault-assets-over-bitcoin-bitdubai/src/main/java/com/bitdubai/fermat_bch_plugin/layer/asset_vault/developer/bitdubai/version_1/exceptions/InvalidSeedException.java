package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 10/4/15.
 */
public class InvalidSeedException extends FermatException {
    public static final String DEFAULT_MESSAGE = "The seed created or loaded is not a valid seed.";

    public InvalidSeedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
