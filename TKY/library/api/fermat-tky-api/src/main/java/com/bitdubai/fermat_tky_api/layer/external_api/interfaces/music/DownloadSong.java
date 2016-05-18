package com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music;

import java.io.Serializable;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/03/16.
 */
public interface DownloadSong extends Serializable {

    /**
     * This method returns the DownloadSong Id.
     * @return
     */
    String getId();

    /**
     * This method returns the DownloadSong name.
     * @return
     */
    String getName();

    /**
     * This method returns the DownloadSong description.
     * @return
     */
    String getDescription();

    /**
     * This method returns the DownloadSong download URL.
     * @return
     */
    String getDownloadURL();

    /**
     * This method returns the DownloadSong download size
     * @return
     */
    long getDownloadSize();

}
