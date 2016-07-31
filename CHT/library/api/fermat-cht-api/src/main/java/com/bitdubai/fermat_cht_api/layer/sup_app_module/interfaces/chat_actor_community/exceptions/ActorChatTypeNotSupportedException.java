package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Eleazar (eorono@protonmail.com) on 2/04/16.
 */
public class ActorChatTypeNotSupportedException extends FermatException {

    private static final String DEFAULT_MESSAGE = "ACTOR TYPE NOT SUPPORTED EXCEPTION";

    public ActorChatTypeNotSupportedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ActorChatTypeNotSupportedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
