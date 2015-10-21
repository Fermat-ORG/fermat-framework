package com.bitdubai.fermat_ccm_api.layer.actor;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_api.layer.actor.CantStartSubsystemException</code>
 * is thrown when there is an error trying to start an actor of the CCP platform.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 */
public class CantStartSubsystemException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T START ACTOR CCP SUBSYSTEM EXCEPTION";

    public CantStartSubsystemException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartSubsystemException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
