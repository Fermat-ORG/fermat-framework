package com.bitdubai.fermat_cht_api.all_definition.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Franklin Marcnao on 23/04/16.
 */
public class CantListChatActorException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T LIST CHAT ACTORS EXCEPTION";

    public CantListChatActorException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListChatActorException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
