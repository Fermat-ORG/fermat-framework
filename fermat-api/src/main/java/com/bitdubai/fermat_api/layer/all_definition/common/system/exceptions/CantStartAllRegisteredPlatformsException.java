package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartAllRegisteredPlatformsException</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/11/2015.
 */
public class CantStartAllRegisteredPlatformsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "ERROR TRYING TO START ALL THE PLATFORMS REGISTERED IN FERMAT SYSTEM";

    public CantStartAllRegisteredPlatformsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartAllRegisteredPlatformsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
