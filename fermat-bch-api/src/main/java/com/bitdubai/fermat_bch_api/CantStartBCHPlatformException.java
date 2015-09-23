package com.bitdubai.fermat_bch_api;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 9/19/15.
 */
public class CantStartBCHPlatformException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error starting the Blockchain Platform.";

    public CantStartBCHPlatformException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
