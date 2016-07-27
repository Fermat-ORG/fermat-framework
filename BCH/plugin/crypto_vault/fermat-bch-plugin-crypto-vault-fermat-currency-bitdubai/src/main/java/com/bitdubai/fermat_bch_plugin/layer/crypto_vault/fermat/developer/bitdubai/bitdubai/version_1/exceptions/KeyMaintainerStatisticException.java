package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 10/7/15.
 */
public class KeyMaintainerStatisticException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was a problem with the Key Maintainer agent.";

    public KeyMaintainerStatisticException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
