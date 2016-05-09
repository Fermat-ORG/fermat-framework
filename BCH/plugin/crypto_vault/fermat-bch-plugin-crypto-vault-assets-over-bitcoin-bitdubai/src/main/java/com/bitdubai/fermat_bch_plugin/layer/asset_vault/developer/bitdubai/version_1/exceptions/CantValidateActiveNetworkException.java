package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 1/15/16.
 */
public class CantValidateActiveNetworkException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error validating the active networks.";
    public CantValidateActiveNetworkException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
