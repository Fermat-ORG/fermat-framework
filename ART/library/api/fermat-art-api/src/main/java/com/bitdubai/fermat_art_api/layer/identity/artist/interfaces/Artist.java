package com.bitdubai.fermat_art_api.layer.identity.artist.interfaces;

import com.bitdubai.fermat_art_api.all_definition.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_art_api.all_definition.enums.ExposureLevel;
import com.bitdubai.fermat_art_api.all_definition.interfaces.ArtIdentity;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public interface Artist extends ArtIdentity {

    /**
     * This method returns the artist exposure level.
     * @return
     */

    ExposureLevel getExposureLevel();

    /**
     * This method returns the artist politics about the connections with fans.
     * @return
     */
    ArtistAcceptConnectionsType getArtistAcceptConnectionsType();

}
