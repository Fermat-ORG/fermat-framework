package com.bitdubai.fermat_art_api.layer.sub_app_module.community.artist.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 4/6/16.
 */
public class ArtistConnectionDenialFailedException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CRYPTO BROKER CONNECTION DENIAL FAILED EXCEPTION";

    public ArtistConnectionDenialFailedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public ArtistConnectionDenialFailedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
