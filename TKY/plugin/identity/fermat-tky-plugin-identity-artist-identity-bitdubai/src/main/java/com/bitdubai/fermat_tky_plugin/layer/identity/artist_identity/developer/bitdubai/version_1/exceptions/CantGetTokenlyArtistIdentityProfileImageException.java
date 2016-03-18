package com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by franklin on 02/11/15.
 */
public class CantGetTokenlyArtistIdentityProfileImageException extends TKYException {


    public CantGetTokenlyArtistIdentityProfileImageException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}