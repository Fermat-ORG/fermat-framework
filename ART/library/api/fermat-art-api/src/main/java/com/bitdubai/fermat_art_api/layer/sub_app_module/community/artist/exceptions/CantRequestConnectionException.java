package com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/6/16.
 */
public class CantRequestConnectionException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T REQUEST CONNECTION EXCEPTION";

    public CantRequestConnectionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRequestConnectionException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
