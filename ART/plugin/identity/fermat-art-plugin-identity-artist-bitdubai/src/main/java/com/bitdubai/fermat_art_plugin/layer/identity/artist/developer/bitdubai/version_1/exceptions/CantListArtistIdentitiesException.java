package com.bitdubai.fermat_art_plugin.layer.identity.artist.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 02/11/15.
 */
public class CantListArtistIdentitiesException extends FermatException {


    public CantListArtistIdentitiesException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
