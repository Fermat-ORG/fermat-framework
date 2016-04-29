package com.bitdubai.fermat_art_api.layer.identity.artist.interfaces;

import com.bitdubai.fermat_art_api.all_definition.interfaces.ArtIdentity;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public interface Artist extends ArtIdentity {

    /**
     * This method returns the external platform username.
     * @return
     */
    String getExternalUsername();

    /**
     * This method returns the Exposure level.
     * @return
     */
    ExposureLevel getExposureLevel();

    /**
     * This Method returns the Artist Accept Connections Type.
     * @return
     */
    ArtistAcceptConnectionsType getArtistAcceptConnectionsType();


}
