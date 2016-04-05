package com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_remote_community.exception;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Eleazar (eorono@protonmail.com) on 3/04/16.
 */
public class CantStartRequestException extends FermatException {

    public CantStartRequestException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
