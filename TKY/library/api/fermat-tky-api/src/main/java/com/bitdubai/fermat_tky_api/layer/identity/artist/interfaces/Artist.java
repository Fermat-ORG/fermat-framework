package com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.Identity;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public interface Artist extends Identity {


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
