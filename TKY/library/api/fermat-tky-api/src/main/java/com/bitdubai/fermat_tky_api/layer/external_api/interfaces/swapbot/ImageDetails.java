package com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot;

import java.sql.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/03/16.
 */
public interface ImageDetails {

    /**
     * Represents the background id.
     * @return
     */
    String getId();

    /**
     * Represents the background full url
     * @return
     */
    String getFullUrl();

    /**
     * Represents the background medium Url.
     * @return
     */
    String mediumUrl();

    /**
     * Represents the thumb Url.
     * @return
     */
    String thumbUrl();

    /**
     * Represents the original url.
     * @return
     */
    String originalUrl();

    /**
     * Represents the content type.
     * @return
     */
    String contentType();

    /**
     * Represents the background size
     * @return
     */
    long getSize();

    /**
     * represents the original file name
     * @return
     */
    String getOriginalFileName();

    /**
     * Represents the last background update.
     * @return
     */
    Date getUpdatedAt();

}
