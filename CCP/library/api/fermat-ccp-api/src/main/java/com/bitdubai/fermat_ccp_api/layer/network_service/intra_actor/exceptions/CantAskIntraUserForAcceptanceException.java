package com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 02/09/15.
 */
public class CantAskIntraUserForAcceptanceException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T ASK AN INTRA USER FOR ACCEPTANCE EXCEPTION.";

    public CantAskIntraUserForAcceptanceException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantAskIntraUserForAcceptanceException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
