package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_settings.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 21/07/15.
 */
public class CantLoadSettingsFileException extends FermatException {

    public static final String DEFAULT_MESSAGE = "ERROR READ REQUESTED SETTINGS XML: ";

    public CantLoadSettingsFileException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
