package com.bitdubai.fermat_bnk_api;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn 18.09.15
 */
public class CantStartBNKPlatformException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error starting the Banking Platform.";
    public CantStartBNKPlatformException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
