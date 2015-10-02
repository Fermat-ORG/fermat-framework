package com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 8/5/15.
 */
public class CantLoadPlatformInformationException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error trying to load platform information values.";
    public CantLoadPlatformInformationException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
