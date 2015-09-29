package com.bitdubai.fermat_csh_api;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn 18.09.15
 */
public class CantStartCSHPlatformException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error starting the Cash Platform.";
    public CantStartCSHPlatformException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
