package com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/03/16.
 */
public interface Song {

    /**
     * This method returns the Song Id.
     * @return
     */
    String getId();

    /**
     * This method returns the Song name.
     * @return
     */
    String getName();

    /**
     * This method returns the Song description.
     * @return
     */
    String getDescription();

    /**
     * This method returns the Song download URL.
     * @return
     */
    String getDownloadURL();

    /**
     * This method returns the Song download size
     * @return
     */
    long getDownloadSize();

}
