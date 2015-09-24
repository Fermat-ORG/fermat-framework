package com.bitdubai.fermat_ccp_api;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.CantStartCCPPlatformException</code>
 * is thrown when we can't start CCP platform.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 */
public class CantStartCCPPlatformException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error starting the Crypto Currency Platform.";

    public CantStartCCPPlatformException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

}
