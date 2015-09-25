package com.bitdubai.fermat_api.layer.ccp_actor.extra_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 04/09/15.
 */
public class CantSignExtraUserMessageException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T SIGN MESSAGE";

    public CantSignExtraUserMessageException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }



}
