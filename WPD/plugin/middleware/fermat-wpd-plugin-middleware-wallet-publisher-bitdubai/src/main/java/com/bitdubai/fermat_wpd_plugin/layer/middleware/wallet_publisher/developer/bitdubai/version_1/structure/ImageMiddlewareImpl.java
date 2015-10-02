/*
 * @#ImageImpl.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.Image;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.ImageMiddlewareImpl</code> is the
 * representation of the ImageMiddleware
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 12/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ImageMiddlewareImpl implements Image {

    /**
     * Represent the FileId
     */
    public UUID fileId;

    /**
     * Represent the Component Id
     */
    public UUID componentId;

    /**
     * Represent the data
     */
    public byte[] data;

    /**
     * Constructor
     */
    public ImageMiddlewareImpl() {
        super();
    }

    /**
     * Constructor whit parameters
     *
     * @param componentId
     * @param fileId
     * @param data
     */
    public ImageMiddlewareImpl(UUID componentId, UUID fileId, byte[] data) {
        super();
        this.componentId = componentId;
        this.fileId = fileId;
        this.data = data;
    }

    /**
     * Get the Component Id
     *
     * @return UUID
     */
    public UUID getComponentId() {
        return componentId;
    }

    /**
     * Set the componentId
     *
     * @param componentId
     */
    public void setComponentId(UUID componentId) {
        this.componentId = componentId;
    }


    /**
     * Get the FileId
     *
     * @return UUID
     */
    public UUID getFileId() {
        return fileId;
    }

    /**
     * Set the fileId
     *
     * @param fileId
     */
    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    /**
     * (non-Javadoc)
     *
     * @see Image#getData()
     */
    @Override
    public byte[] getData() {
        return data;
    }

    /**
     * Set the data
     *
     * @param data
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageMiddlewareImpl)) return false;
        ImageMiddlewareImpl image = (ImageMiddlewareImpl) o;
        return Objects.equals(getFileId(), image.getFileId());
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(getFileId());
    }

    /**
     * (non-Javadoc)
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "ImageMiddlewareImpl{" +
                "componentId=" + componentId +
                ", fileId=" + fileId +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
