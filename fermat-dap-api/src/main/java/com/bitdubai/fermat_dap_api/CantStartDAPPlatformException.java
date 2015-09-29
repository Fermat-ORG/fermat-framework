package com.bitdubai.fermat_dap_api;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 9/4/15.
 */
public class CantStartDAPPlatformException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error starting the Digital Assets Platform.";
    public CantStartDAPPlatformException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
