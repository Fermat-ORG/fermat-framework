package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_local_community.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by root on 31/03/16.
 */
public class CantGetSelectActorException extends FermatException {
    private static final String DEFAULT_MESSAGE = "CANT GET SELECTED ACTOR EXCEPTION";

    public CantGetSelectActorException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetSelectActorException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
