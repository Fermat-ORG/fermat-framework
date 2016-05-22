package com.bitdubai.fermat_art_api.layer.identity.fan.interfaces;

import com.bitdubai.fermat_art_api.all_definition.interfaces.ArtIdentity;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/03/16.
 */
public interface Fanatic extends ArtIdentity, Serializable {

    /**
     * This method returns external username.
     * @return
     */
    String getExternalUsername();

}
