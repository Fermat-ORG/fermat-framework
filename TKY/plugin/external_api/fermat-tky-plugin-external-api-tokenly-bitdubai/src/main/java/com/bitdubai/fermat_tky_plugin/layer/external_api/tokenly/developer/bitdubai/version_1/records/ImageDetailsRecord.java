package com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.version_1.records;


import com.bitdubai.fermat_tky_api.layer.external_api.interfaces.swapbot.ImageDetails;

import java.sql.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/03/16.
 */
public class ImageDetailsRecord implements ImageDetails {

    String id;
    String fullUrl;
    String mediumUrl;
    String thumbUrl;
    String originalUrl;
    String contentType;
    long size;
    String originalFileName;
    Date updatedAt;

    public ImageDetailsRecord(
            String id,
            String fullUrl,
            String mediumUrl,
            String thumbUrl,
            String originalUrl,
            String contentType,
            long size,
            String originalFileName,
            Date updatedAt) {
        this.id = id;
        this.fullUrl = fullUrl;
        this.mediumUrl = mediumUrl;
        this.thumbUrl = thumbUrl;
        this.originalUrl = originalUrl;
        this.contentType = contentType;
        this.size = size;
        this.originalFileName = originalFileName;
        this.updatedAt = updatedAt;
    }

    /**
     * This method returns the Image detail id
     * @return
     */
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * This method returns the Image detail full url.
     * @return
     */
    @Override
    public String getFullUrl() {
        return this.fullUrl;
    }

    /**
     * This method returns the Image detail medium url.
     * @return
     */
    @Override
    public String mediumUrl() {
        return this.mediumUrl;
    }

    /**
     * This method returns the Image thumb url.
     * @return
     */
    @Override
    public String thumbUrl() {
        return this.thumbUrl;
    }

    /**
     * This method returns the Image original url.
     * @return
     */
    @Override
    public String originalUrl() {
        return this.originalUrl;
    }

    /**
     * This method returns the Image content type.
     * @return
     */
    @Override
    public String contentType() {
        return this.contentType;
    }

    /**
     * This method returns the Image thumb size.
     * @return
     */
    @Override
    public long getSize() {
        return this.size;
    }

    /**
     * This method returns the Image thumb original file name.
     * @return
     */
    @Override
    public String getOriginalFileName() {
        return this.originalFileName;
    }

    /**
     * This method returns the Image thumb updated date.
     * @return
     */
    @Override
    public Date getUpdatedAt() {
        return this.updatedAt;
    }
}
