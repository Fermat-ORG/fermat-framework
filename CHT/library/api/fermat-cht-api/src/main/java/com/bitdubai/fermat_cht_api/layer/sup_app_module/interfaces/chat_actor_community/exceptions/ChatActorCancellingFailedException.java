package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Eleazar (eorono@protonmail.com) on 2/04/16.
 */
public class ChatActorCancellingFailedException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CHAT ACTOR LOCAL CANCELLING FAILED EXCEPTION";

    public ChatActorCancellingFailedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ChatActorCancellingFailedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
