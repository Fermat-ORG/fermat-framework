package com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 11/11/15.
 */
public class CantUpdateIntraUserIdentityException extends FermatException {


    public CantUpdateIntraUserIdentityException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }


}