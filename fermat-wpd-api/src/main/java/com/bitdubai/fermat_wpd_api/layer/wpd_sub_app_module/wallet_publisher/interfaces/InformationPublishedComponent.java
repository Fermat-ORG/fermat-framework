/*
 * @#InformationPublishedComponent.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */

package com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.enums.ComponentPublishedInformationStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.enums.InformationPublishedComponentType;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.InformationPublishedComponentMiddleware</code> define
 * the static information about a published component.
 * <p/>
 *
 * @author Ezequiel Postan (ezequiel.postan@gmail.com)
 * @author Update by Roberto Requena - (rart3001@gmail.com) on 06/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface InformationPublishedComponent {

    /**
     * Get the id
     *
     * @return UUID
     */
    public UUID getId();

    /**
     * Get the Descriptor Factory Project Id
     *
     * @return String
     */
    public String getWalletFactoryProjectId();

    /**
     * Get the Descriptor Factory Project Name
     *
     * @return String
     */
    public String getWalletFactoryProjectName();

    /**
     * Get the Type
     *
     * @return InformationPublishedComponentType
     */
    public InformationPublishedComponentType getType();

    /**
     * Get the Descriptions
     *
     * @return String
     */
    public String getDescriptions();

    /**
     * Get the icon image
     *
     * @return ImageMiddleware
     */
    public Image getIconImg();

    /**
     * Get the main screen shot image
     *
     * @return ImageMiddleware
     */
    public Image getMainScreenShotImg();

    /**
     * Get the video url
     *
     * @return URL
     */
    public URL getVideoUrl();

    /**
     * Get the Status
     *
     * @return ComponentPublishedInformationStatus
     */
    public ComponentPublishedInformationStatus getStatus();

    /**
     * Get the Status Timestamp
     *
     * @return Timestamp
     */
    public Timestamp getStatusTimestamp();

    /**
     * Get the Publication Timestamp
     *
     * @return Timestamp
     */
    public Timestamp getPublicationTimestamp();

    /**
     * Get the Publisher Id
     *
     * @return String
     */
    public String getPublisherIdentityPublicKey();

    /**
     * Get the Signature for this component
     *
     * @return String
     */
    public String getSignature();

    /**
     * Get the Component Versions Details List
     *
     * @return List<ComponentVersionDetailMiddleware>
     */
    public List<ComponentVersionDetail> getComponentVersionDetailList();

    /**
     * Get the Screens Shots Component List
     *
     * @return List<ImageMiddleware>
     */
    public List<Image> getScreensShotsComponentList();

}
