package com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/03/16.
 */
public interface Album extends Serializable {

    /**
     * This method returns the Album id.
     * @return
     */
    String getId();

    /**
     * This method returns the Album name.
     * @return
     */
    String getName();

    /**
     * This method returns the Album description.
     * @return
     */
    String getDescription();

    /**
     * This method returns the Album song count.
     * @return
     */
    int getSongCount();

    /**
     * This method returns the album songs.
     * @return
     */
    Song[] getSongs();

}
