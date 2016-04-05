package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */

public class ChatRemoteCancellingFailedException extends FermatException {

    public ChatRemoteCancellingFailedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
