package com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_factory.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 9/23/15.
 */
public class CantGetInstalledWalletsException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error trying to get the installed wallets in the platform.";

    public CantGetInstalledWalletsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
