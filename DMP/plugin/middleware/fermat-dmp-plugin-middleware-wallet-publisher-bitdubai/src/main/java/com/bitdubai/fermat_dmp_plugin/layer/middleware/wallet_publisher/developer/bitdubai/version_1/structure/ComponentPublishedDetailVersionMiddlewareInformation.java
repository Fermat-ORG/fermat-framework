/*
 * @#WalletPublishedMiddlewareInformation.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.DescriptorFactoryProjectType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.enums.ComponentPublishedInformationStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.ComponentPublishedInformation;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.ComponentPublishedMiddlewareInformation</code> is the
 * representation of the Component Published Middleware Information
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ComponentPublishedDetailVersionMiddlewareInformation implements ComponentPublishedInformation {

    /**
     * Represent the id
     */
    private UUID id;

    /**
     * Represent the walletFactoryProjectId
     */
    private UUID walletFactoryProjectId;

    /**
     * Represent the walletFactoryProjectName
     */
    private String walletFactoryProjectName;

    /**
     * Represent the walletId
     */
    private UUID walletId;

    /**
     * Represent the catalogId
     */
    private UUID catalogId;

    /**
     * Represent the storeId
     */
    private UUID storeId;

    /**
     * Represent the screenSize
     */
    private ScreenSize screenSize;

    /**
     * Represent the initialWalletVersion
     */
    private Version initialWalletVersion;

    /**
     * Represent the finalWalletVersion
     */
    private Version finalWalletVersion;

    /**
     * Represent the initialPlatformVersion
     */
    private Version initialPlatformVersion;

    /**
     * Represent the finalPlatformVersion
     */
    private Version finalPlatformVersion;

    /**
     * Represent the type
     */
    private DescriptorFactoryProjectType type;

    /**
     * Represent the version
     */
    private Version version;

    /**
     * Represent the versionTimestamp
     */
    private Timestamp versionTimestamp;

    /**
     * Represent the status
     */
    private ComponentPublishedInformationStatus status;

    /**
     * Represent the statusTimestamp
     */
    private Timestamp statusTimestamp;

    /**
     * Represent the publicationTimestamp
     */
    private Timestamp publicationTimestamp;

    /**
     * Represent the publisherId
     */
    private UUID publisherId;

    /**
     * Constructor
     */
    public ComponentPublishedDetailVersionMiddlewareInformation(){
        super();
    }


    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public UUID getCatalogId() {
        return catalogId;
    }

    /**
     * Set the catalogId
     *
     * @param catalogId
     */
    public void setCatalogId(UUID catalogId) {
        this.catalogId = catalogId;
    }

    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public Version getFinalPlatformVersion() {
        return finalPlatformVersion;
    }

    /**
     * Set the finalPlatformVersion
     *
     * @param finalPlatformVersion
     */
    public void setFinalPlatformVersion(Version finalPlatformVersion) {
        this.finalPlatformVersion = finalPlatformVersion;
    }


    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public Version getFinalWalletVersion() {
        return finalWalletVersion;
    }

    /**
     * Set the finalWalletVersion
     *
     * @param finalWalletVersion
     */
    public void setFinalWalletVersion(Version finalWalletVersion) {
        this.finalWalletVersion = finalWalletVersion;
    }


    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * Set the id
     *
     * @param id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public Version getInitialPlatformVersion() {
        return initialPlatformVersion;
    }

    /**
     * Set the initialPlatformVersion
     *
     * @param initialPlatformVersion
     */
    public void setInitialPlatformVersion(Version initialPlatformVersion) {
        this.initialPlatformVersion = initialPlatformVersion;
    }

    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public Version getInitialWalletVersion() {
        return initialWalletVersion;
    }

    /**
     * Set the initialWalletVersion
     *
     * @param initialWalletVersion
     */
    public void setInitialWalletVersion(Version initialWalletVersion) {
        this.initialWalletVersion = initialWalletVersion;
    }

    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public Timestamp getPublicationTimestamp() {
        return publicationTimestamp;
    }

    /**
     * Set the publicationTimestamp
     *
     * @param publicationTimestamp
     */
    public void setPublicationTimestamp(Timestamp publicationTimestamp) {
        this.publicationTimestamp = publicationTimestamp;
    }

    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public UUID getPublisherId() {
        return publisherId;
    }

    /**
     * Set the publisherId
     *
     * @param publisherId
     */
    public void setPublisherId(UUID publisherId) {
        this.publisherId = publisherId;
    }

    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public ScreenSize getScreenSize() {
        return screenSize;
    }

    /**
     * Set the screenSize
     *
     * @param screenSize
     */
    public void setScreenSize(ScreenSize screenSize) {
        this.screenSize = screenSize;
    }

    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public ComponentPublishedInformationStatus getStatus() {
        return status;
    }

    /**
     * Set the status
     *
     * @param status
     */
    public void setStatus(ComponentPublishedInformationStatus status) {
        this.status = status;
    }

    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public Timestamp getStatusTimestamp() {
        return statusTimestamp;
    }

    /**
     * Set the statusTimestamp
     *
     * @param statusTimestamp
     */
    public void setStatusTimestamp(Timestamp statusTimestamp) {
        this.statusTimestamp = statusTimestamp;
    }

    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public UUID getStoreId() {
        return storeId;
    }

    /**
     * Set the storeId
     *
     * @param storeId
     */
    public void setStoreId(UUID storeId) {
        this.storeId = storeId;
    }

    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public DescriptorFactoryProjectType getType() {
        return type;
    }

    /**
     * Set the type
     *
     * @param type
     */
    public void setType(DescriptorFactoryProjectType type) {
        this.type = type;
    }

    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public Version getVersion() {
        return version;
    }

    /**
     * Set the version
     *
     * @param version
     */
    public void setVersion(Version version) {
        this.version = version;
    }

    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public Timestamp getVersionTimestamp() {
        return versionTimestamp;
    }

    /**
     * Set the versionTimestamp
     *
     * @param versionTimestamp
     */
    public void setVersionTimestamp(Timestamp versionTimestamp) {
        this.versionTimestamp = versionTimestamp;
    }

    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public UUID getDescriptorFactoryProjectId() {
        return walletFactoryProjectId;
    }

    /**
     * Set the walletFactoryProjectId
     *
     * @param walletFactoryProjectId
     */
    public void setWalletFactoryProjectId(UUID walletFactoryProjectId) {
        this.walletFactoryProjectId = walletFactoryProjectId;
    }

    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public String getDescriptorFactoryProjectName() {
        return walletFactoryProjectName;
    }

    /**
     * Set the walletFactoryProjectName
     *
     * @param walletFactoryProjectName
     */
    public void setWalletFactoryProjectName(String walletFactoryProjectName) {
        this.walletFactoryProjectName = walletFactoryProjectName;
    }

    /**
     * (non-Javadoc)
     * @see ComponentPublishedInformation#getCatalogId()
     */
    @Override
    public UUID getWalletId() {
        return walletId;
    }

    /**
     * Set the walletId
     *
     * @param walletId
     */
    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }
}
