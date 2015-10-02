/*
 * @#InformationPublishedComponentImpl.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_publisher.enums.ComponentPublishedInformationStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.enums.InformationPublishedComponentType;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.ComponentVersionDetail;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.Image;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.InformationPublishedComponent;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.InformationPublishedComponentMiddlewareImpl</code> is the
 * representation of the Information Published Component
 * <p/>
 * 
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class InformationPublishedComponentMiddlewareImpl implements InformationPublishedComponent {

    /**
     * Represent the id
     */
    private UUID id;

    /**
     * Represent the Wallet Factory Project Id
     */
    private String walletFactoryProjectId;

    /**
     * Represent the Wallet Factory Project Name
     */
    private String walletFactoryProjectName;

    /**
     * Represent the Type
     */
    private InformationPublishedComponentType type;

    /**
     * Represent the Descriptions
     */
    private String descriptions;

    /**
     * Represent the icon image
     */
    private Image iconImg;

    /**
     * Represent the main screen shot image
     */
    private Image mainScreenShotImg;

    /**
     * Represent the video url
     */
    private URL videoUrl;

    /**
     * Represent the Status
     */
    private ComponentPublishedInformationStatus status;

    /**
     * Represent the Status Timestamp
     */
    private Timestamp statusTimestamp;

    /**
     * Represent the Publication Timestamp
     */
    private Timestamp publicationTimestamp;

    /**
     * Represent the publisherIdentityPublicKey
     */
    private String publisherIdentityPublicKey;

    /**
     * Represent the signature
     */
    private String signature;

    /**
     * Represent the Component Versions Details List
     */
    private List<ComponentVersionDetail> componentVersionDetailList;

    /**
     * Represent the Screens Shots Component List
     */
    private List<Image> screensShotsComponentList;

    /**
     * Constructor
     */
    public InformationPublishedComponentMiddlewareImpl(){
        super();
        this.componentVersionDetailList = new ArrayList<>();
        this.screensShotsComponentList    = new ArrayList<>();
    }

    /**
     * Constructor with parameters
     *
     * @param componentVersionDetailList
     * @param descriptions
     * @param walletFactoryProjectId
     * @param walletFactoryProjectName
     * @param iconImg
     * @param id
     * @param mainScreenShotImg
     * @param publicationTimestamp
     * @param publisherIdentityPublicKey
     * @param screensShotsComponentList
     * @param status
     * @param statusTimestamp
     * @param type
     * @param videoUrl
     */
    public InformationPublishedComponentMiddlewareImpl(List<ComponentVersionDetail> componentVersionDetailList, String descriptions, String walletFactoryProjectId, String walletFactoryProjectName, Image iconImg, UUID id, Image mainScreenShotImg, Timestamp publicationTimestamp, String publisherIdentityPublicKey, List<Image> screensShotsComponentList, ComponentPublishedInformationStatus status, Timestamp statusTimestamp, InformationPublishedComponentType type, URL videoUrl) {
        super();
        this.componentVersionDetailList = componentVersionDetailList;
        this.descriptions = descriptions;
        this.walletFactoryProjectId = walletFactoryProjectId;
        this.walletFactoryProjectName = walletFactoryProjectName;
        this.iconImg = iconImg;
        this.id = id;
        this.mainScreenShotImg = mainScreenShotImg;
        this.publicationTimestamp = publicationTimestamp;
        this.publisherIdentityPublicKey = publisherIdentityPublicKey;
        this.screensShotsComponentList = screensShotsComponentList;
        this.status = status;
        this.statusTimestamp = statusTimestamp;
        this.type = type;
        this.videoUrl = videoUrl;
    }

    /**
     * (non-Javadoc)
     * @see InformationPublishedComponentMiddlewareImpl#getComponentVersionDetailList()
     */
    @Override
    public List<ComponentVersionDetail> getComponentVersionDetailList() {
        return (List<ComponentVersionDetail>) componentVersionDetailList;
    }


    /**
     * Set the componentVersionDetailList
     *
     * @param componentVersionDetailList
     */
    public void setComponentVersionDetailList(List<ComponentVersionDetail> componentVersionDetailList) {
        this.componentVersionDetailList = componentVersionDetailList;
    }

    /**
     * (non-Javadoc)
     * @see InformationPublishedComponentMiddlewareImpl#getDescriptions()
     */
    @Override
    public String getDescriptions() {
        return descriptions;
    }


    /**
     * Set the descriptions
     *
     * @param descriptions
     */
    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    /**
     * (non-Javadoc)
     * @see InformationPublishedComponentMiddlewareImpl#getWalletFactoryProjectId()
     */
    @Override
    public String getWalletFactoryProjectId() {
        return walletFactoryProjectId;
    }


    /**
     * Set the walletFactoryProjectId
     *
     * @param walletFactoryProjectId
     */
    public void setWalletFactoryProjectId(String walletFactoryProjectId) {
        this.walletFactoryProjectId = walletFactoryProjectId;
    }

    /**
     * (non-Javadoc)
     * @see InformationPublishedComponentMiddlewareImpl#getWalletFactoryProjectName()
     */
    @Override
    public String getWalletFactoryProjectName() {
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
     * @see InformationPublishedComponentMiddlewareImpl#getIconImg()
     */
    @Override
    public Image getIconImg() {
        return iconImg;
    }


    /**
     * Set the iconImg
     *
     * @param iconImg
     */
    public void setIconImg(Image iconImg) {
        this.iconImg = iconImg;
    }

    /**
     * (non-Javadoc)
     * @see InformationPublishedComponentMiddlewareImpl#getId()
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
     * @see InformationPublishedComponentMiddlewareImpl#getMainScreenShotImg()
     */
    @Override
    public Image getMainScreenShotImg() {
        return mainScreenShotImg;
    }


    /**
     * Set the mainScreenShotImg
     *
     * @param mainScreenShotImg
     */
    public void setMainScreenShotImg(Image mainScreenShotImg) {
        this.mainScreenShotImg = mainScreenShotImg;
    }

    /**
     * (non-Javadoc)
     * @see InformationPublishedComponentMiddlewareImpl#getPublicationTimestamp()
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
     * @see InformationPublishedComponentMiddlewareImpl#getPublisherIdentityPublicKey()
     */
    @Override
    public String getPublisherIdentityPublicKey() {
        return publisherIdentityPublicKey;
    }

    /**
     * Set the publisherIdentityPublicKey
     *
     * @param publisherIdentityPublicKey
     */
    public void setPublisherIdentityPublicKey(String publisherIdentityPublicKey) {
        this.publisherIdentityPublicKey = publisherIdentityPublicKey;
    }

    /**
     * (non-Javadoc)
     * @see InformationPublishedComponentMiddlewareImpl#getSignature()
     */
    @Override
    public String getSignature() {
        return signature;
    }

    /**
     * Set the Signature
     *
     * @param signature
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * (non-Javadoc)
     * @see InformationPublishedComponentMiddlewareImpl#getScreensShotsComponentList()
     */
    @Override
    public List<Image> getScreensShotsComponentList() {
        return screensShotsComponentList;
    }


    /**
     * Set the screensShotsComponentList
     *
     * @param screensShotsComponentList
     */
    public void setScreensShotsComponentList(List<Image> screensShotsComponentList) {
        this.screensShotsComponentList = screensShotsComponentList;
    }

    /**
     * (non-Javadoc)
     * @see InformationPublishedComponentMiddlewareImpl#getStatus()
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
     * @see InformationPublishedComponentMiddlewareImpl#getStatusTimestamp()
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
     * @see InformationPublishedComponentMiddlewareImpl#getType()
     */
    @Override
    public InformationPublishedComponentType getType() {
        return type;
    }

    /**
     * Set the type
     *
     * @param type
     */
    public void setType(InformationPublishedComponentType type) {
        this.type = type;
    }

    /**
     * (non-Javadoc)
     * @see InformationPublishedComponentMiddlewareImpl#getVideoUrl()
     */
    @Override
    public URL getVideoUrl() {
        return videoUrl;
    }

    /**
     * Set the videoUrl
     *
     * @param videoUrl
     */
    public void setVideoUrl(URL videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     * (non-Javadoc)
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InformationPublishedComponentMiddlewareImpl)) return false;
        InformationPublishedComponentMiddlewareImpl that = (InformationPublishedComponentMiddlewareImpl) o;
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
        return "InformationPublishedComponentMiddlewareImpl{" +
                "componentVersionDetailList=" + componentVersionDetailList +
                ", id=" + id +
                ", walletFactoryProjectId=" + walletFactoryProjectId +
                ", walletFactoryProjectName='" + walletFactoryProjectName + '\'' +
                ", type=" + type +
                ", descriptions='" + descriptions + '\'' +
                ", iconImg=" + iconImg +
                ", mainScreenShotImg=" + mainScreenShotImg +
                ", videoUrl=" + videoUrl +
                ", status=" + status +
                ", statusTimestamp=" + statusTimestamp +
                ", publicationTimestamp=" + publicationTimestamp +
                ", publisherIdentityPublicKey=" + publisherIdentityPublicKey +
                ", screensShotsComponentList=" + screensShotsComponentList +
                '}';
    }
}
