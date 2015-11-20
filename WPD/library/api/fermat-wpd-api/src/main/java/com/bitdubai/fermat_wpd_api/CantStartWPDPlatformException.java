package com.bitdubai.fermat_wpd_api;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Nerio on 29/09/15.
 */
public class CantStartWPDPlatformException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error starting the Crypto Broker Platform.";

    public CantStartWPDPlatformException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
