package com.bitdubai.fermat_cht_plugin.layer.actor_connection.chat.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 06/04/16.
 */
public class ChatActorConnectionNotStartedException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CHAT ACTOR CONNECTION NOT STARTED EXCEPTION";

    public ChatActorConnectionNotStartedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ChatActorConnectionNotStartedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public ChatActorConnectionNotStartedException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }
}
