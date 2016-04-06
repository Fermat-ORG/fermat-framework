package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Eleazar (eorono@protonmail.com) on 2/04/16.
 */
public class CantListChatIdentitiesToSelectException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T LIST ACTOR IDENTITIES TO SELECT EXCEPTION";

    public CantListChatIdentitiesToSelectException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListChatIdentitiesToSelectException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
