package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 11/08/15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public class CantPersistProfileImageException  extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T PERSIST PROFILE IMAGE EXCEPTION";

    public CantPersistProfileImageException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantPersistProfileImageException(final Exception cause, final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }


}