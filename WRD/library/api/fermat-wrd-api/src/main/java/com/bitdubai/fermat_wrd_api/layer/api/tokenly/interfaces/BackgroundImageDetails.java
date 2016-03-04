package com.bitdubai.fermat_wrd_api.layer.api.tokenly.interfaces;

import java.util.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/03/16.
 */
public interface BackgroundImageDetails {

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
