/*
 * @#ComponentPublishedInformation.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */

package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.enums.ComponentPublishedInformationStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.enums.ComponentPublishedInformationType;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.ComponentPublishedInformation</code> define
 * the static information about a published component.
 * <p/>
 *
 * @author Ezequiel Postan (ezequiel.postan@gmail.com)
 * @author Update by Roberto Requena - (rart3001@gmail.com) on 06/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface ComponentPublishedInformation {

    /**
     * Get the id
     *
     * @return UUID
     */
    public UUID getId();

    /**
     * Get the Wallet Factory Project Id
     *
     * @return UUID
     */
    public UUID getWalletFactoryProjectId();

    /**
     * Get the Wallet Factory Project Name
     *
     * @return String
     */
    public String getWalletFactoryProjectName();

    /**
     * Get the Wallet Id
     *
     * @return UUID
     */
    public UUID getWalletId();

    /**
     * Get the Catalog Id
     *
     * @return UUID
     */
    public UUID getCatalogId();

    /**
     * Get the Store Id
     *
     * @return UUID
     */
    public UUID getStoreId();

    /**
     * Get the Screen Size
     *
     * @return ScreenSize
     */
    public ScreenSize getScreenSize();

    /**
     * Get the Initial Wallet Version Supported
     *
     * @return Version
     */
    public Version getInitialWalletVersion();

    /**
     * Get the Final Wallet Version Supported
     *
     * @return Version
     */
    public Version getFinalWalletVersion();

    /**
     * Get the Initial Platform Version Supported
     *
     * @return Version
     */
    public Version getInitialPlatformVersion();

    /**
     * Get the Final Platform Version Supported
     *
     * @return Version
     */
    public Version getFinalPlatformVersion();

    /**
     * Get the Type
     *
     * @return ComponentPublishedInformationType
     */
    public ComponentPublishedInformationType getType();

    /**
     * Get the Version
     *
     * @return Version
     */
    public Version getVersion();

    /**
     * Get the Version Timestamp
     *
     * @return Timestamp
     */
    public Timestamp getVersionTimestamp();

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
     * @return UUID
     */
    public UUID getPublisherId();

}
