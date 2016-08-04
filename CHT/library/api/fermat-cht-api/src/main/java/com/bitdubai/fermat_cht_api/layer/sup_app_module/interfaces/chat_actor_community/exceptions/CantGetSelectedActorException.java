package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Eleazar Oroño (eorono@protonmail.com) on 1/04/16.
 */

public class CantGetSelectedActorException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET SELECTED IDENTITY EXCEPTION";

    public CantGetSelectedActorException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetSelectedActorException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
