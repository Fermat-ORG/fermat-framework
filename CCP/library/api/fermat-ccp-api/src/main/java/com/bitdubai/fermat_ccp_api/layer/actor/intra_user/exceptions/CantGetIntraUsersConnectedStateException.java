package com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 21/12/15.
 */
public class CantGetIntraUsersConnectedStateException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET INTRA USER CONNECTION STATUS EXCEPTION";

    public CantGetIntraUsersConnectedStateException(String message, String context, String possibleReason) {
        this(message, null, context, possibleReason);
    }

    public CantGetIntraUsersConnectedStateException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetIntraUsersConnectedStateException(String context, String possibleReason) {
        super(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
