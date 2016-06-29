package com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantSetPlatformInformationException</code>
 * is thrown when there is an error trying to set platform information.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 27/10/2015.
 */
public class CantSetPlatformInformationException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T SET PLATFORM INFORMATION EXCEPTION";

    public CantSetPlatformInformationException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSetPlatformInformationException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
