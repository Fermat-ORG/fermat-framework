package com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_art_api.all_definition.exceptions.ARTException;

/**
 * Created by franklin on 02/11/15.
 */
public class CantPersistProfileImageException extends ARTException {


    public CantPersistProfileImageException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
