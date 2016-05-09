package com.bitdubai.fermat_tky_api.layer.external_api.interfaces.music;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 13/03/16.
 */
public interface Song extends Serializable {

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
     * This method returns the Song tokens
     * @return
     */
    String[] getTokens();

    /**
     * This method returns the song performers.
     * @return
     */
    String getPerformers();

    /**
     * This method returns the song composers.
     * @return
     */
    String getComposers();

    /**
     * This method returns the song release date.
     * @return
     */
    Date getReleaseDate();

    /**
     * This method returns the song lyrics.
     * @return
     */
    String getLyrics();

    /**
     * This method returns the song credits
     * @return
     */
    String getCredits();

    /**
     * This method returns the song copyright.
     * @return
     */
    String getCopyright();

    /**
     * This method returns the song ownership.
     * @return
     */
    String getOwnership();

    /**
     * This method returns the song usage rights.
     * @return
     */
    String getUsageRights();

    /**
     * This method returns the song usage prohibitions.
     * @return
     */
    String getUsageProhibitions();

    /**
     * This method returns the song bitcoin address.
     * @return
     */
    String getBitcoinAddress();

    /**
     * This method returns the song 'other' field.
     * @return
     */
    String getOther();

    /**
     * Represents the song download URL.
     * @return
     */
    String getDownloadUrl();

}
