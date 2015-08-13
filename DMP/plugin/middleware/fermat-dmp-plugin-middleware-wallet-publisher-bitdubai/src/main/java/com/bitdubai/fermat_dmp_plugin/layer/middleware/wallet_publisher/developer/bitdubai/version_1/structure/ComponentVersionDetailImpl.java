/*
 * @#ComponentVersionDetailImpl.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.ComponentVersionDetail;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.ComponentVersionDetailImpl</code> is the
 * representation of the Component Version Detail
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ComponentVersionDetailImpl implements ComponentVersionDetail {

    /**
     * Represent the id
     */
    private UUID id;

    /**
     * Represent the Catalog Id
     */
    private UUID catalogId;

    /**
     * Represent the Screen Size
     */
    private ScreenSize screenSize;

    /**
     * Represent the Initial Wallet Version Supported
     */
    private Version initialWalletVersion;

    /**
     * Represent the Final Wallet Version Supported
     */
    private Version finalWalletVersion;

    /**
     * Represent the Initial Platform Version Supported
     */
    private Version initialPlatformVersion;

    /**
     * Represent the Final Platform Version Supported
     */
    private Version finalPlatformVersion;

    /**
     * Represent the Version
     */
    private Version version;

    /**
     * Represent the Version Timestamp
     */
    private Timestamp versionTimestamp;

    /**
     * Represent the Component Id
     */
    private UUID componentId;

    /**
     * Represent the observations
     */
    private String observations;


    /**
     * Constructor
     */
    public ComponentVersionDetailImpl(){
        super();
    }

    /**
     * Constructor with parameters
     *
     * @param catalogId
     * @param componentId
     * @param finalPlatformVersion
     * @param finalWalletVersion
     * @param id
     * @param initialPlatformVersion
     * @param initialWalletVersion
     * @param observations
     * @param screenSize
     * @param version
     * @param versionTimestamp
     */
    public ComponentVersionDetailImpl(UUID catalogId, UUID componentId, Version finalPlatformVersion, Version finalWalletVersion, UUID id, Version initialPlatformVersion, Version initialWalletVersion, String observations, ScreenSize screenSize, Version version, Timestamp versionTimestamp) {
        super();
        this.catalogId = catalogId;
        this.componentId = componentId;
        this.finalPlatformVersion = finalPlatformVersion;
        this.finalWalletVersion = finalWalletVersion;
        this.id = id;
        this.initialPlatformVersion = initialPlatformVersion;
        this.initialWalletVersion = initialWalletVersion;
        this.observations = observations;
        this.screenSize = screenSize;
        this.version = version;
        this.versionTimestamp = versionTimestamp;
    }

    /**
     * (non-Javadoc)
     * @see ComponentVersionDetail#getCatalogId()
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
     * @see ComponentVersionDetail#getComponentId()
     */
    @Override
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
     * (non-Javadoc)
     * @see ComponentVersionDetail#getFinalPlatformVersion()
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
     * @see ComponentVersionDetail#getFinalWalletVersion()
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
     * @see ComponentVersionDetail#getId()
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
     * @see ComponentVersionDetail#getInitialPlatformVersion()
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
     * @see ComponentVersionDetail#getInitialWalletVersion()
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
     * @see ComponentVersionDetail#getObservations()
     */
    @Override
    public String getObservations() {
        return observations;
    }

    /**
     * Set the observations
     *
     * @param observations
     */
    public void setObservations(String observations) {
        this.observations = observations;
    }

    /**
     * (non-Javadoc)
     * @see ComponentVersionDetail#getScreenSize()
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
     * @see ComponentVersionDetail#getVersion()
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
     * @see ComponentVersionDetail#getVersionTimestamp()
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
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComponentVersionDetailImpl)) return false;
        ComponentVersionDetailImpl that = (ComponentVersionDetailImpl) o;
        return Objects.equals(getId(), that.getId());
    }

    /**
     * (non-Javadoc)
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * (non-Javadoc)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "ComponentVersionDetailImpl{" +
                "catalogId=" + catalogId +
                ", id=" + id +
                ", screenSize=" + screenSize +
                ", initialWalletVersion=" + initialWalletVersion +
                ", finalWalletVersion=" + finalWalletVersion +
                ", initialPlatformVersion=" + initialPlatformVersion +
                ", finalPlatformVersion=" + finalPlatformVersion +
                ", version=" + version +
                ", versionTimestamp=" + versionTimestamp +
                ", componentId=" + componentId +
                ", observations='" + observations + '\'' +
                '}';
    }
}
