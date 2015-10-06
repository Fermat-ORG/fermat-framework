package com.bitdubai.fermat_ccp_api.layer.identity;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.identity.CantStartSubsystemException</code>
 * is thrown when there is an error trying to start an identity of the CCP platform.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 */
public class CantStartSubsystemException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T START IDENTITY CCP SUBSYSTEM EXCEPTION";

    public CantStartSubsystemException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartSubsystemException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
