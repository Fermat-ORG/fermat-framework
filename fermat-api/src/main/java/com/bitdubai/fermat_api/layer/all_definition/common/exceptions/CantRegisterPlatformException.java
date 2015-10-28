package com.bitdubai.fermat_api.layer.all_definition.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantRegisterPlatformException</code>
 * is thrown when there is an error trying to register a platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 21/10/2015.
 */
public class CantRegisterPlatformException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T REGISTER PLATFORM EXCEPTION";

    public CantRegisterPlatformException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRegisterPlatformException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantRegisterPlatformException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
