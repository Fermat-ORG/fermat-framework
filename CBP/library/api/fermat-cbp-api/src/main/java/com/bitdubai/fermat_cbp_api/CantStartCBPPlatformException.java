package com.bitdubai.fermat_cbp_api;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 9/4/15.
 */
public class CantStartCBPPlatformException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error starting the Crypto Broker Platform.";
    public CantStartCBPPlatformException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
