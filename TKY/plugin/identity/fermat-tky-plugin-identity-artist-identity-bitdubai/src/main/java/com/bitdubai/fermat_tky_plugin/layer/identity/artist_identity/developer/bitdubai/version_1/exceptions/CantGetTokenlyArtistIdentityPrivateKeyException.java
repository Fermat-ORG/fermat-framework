package com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.TKYException;

/**
 * Created by Gabriel Araujo 16/03/16.
 */
public class CantGetTokenlyArtistIdentityPrivateKeyException extends TKYException {


    public CantGetTokenlyArtistIdentityPrivateKeyException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }


}
