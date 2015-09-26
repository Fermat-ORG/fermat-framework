package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_settings.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 22/07/15.
 */
public class InvalidWalletIdException extends FermatException {

    public static final String DEFAULT_MESSAGE = "REQUESTED ID IS NOT EQUALS TO REQUESTED ID IN THE XML FILE: ";

    public InvalidWalletIdException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
