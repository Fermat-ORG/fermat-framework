/*
 * @#InformationPublishedComponentImpl.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.enums.ComponentPublishedInformationStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.DescriptorFactoryProjectType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.ComponentVersionDetail;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.Image;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.InformationPublishedComponent;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.InformationPublishedComponentImpl</code> is the
 * representation of the Information Published Component
 * <p/>
 * 
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class InformationPublishedComponentImpl implements InformationPublishedComponent {

    /**
     * Represent the id
     */
    private UUID id;

    /**
     * Represent the Descriptor Factory Project Id
     */
    private UUID descriptorFactoryProjectId;

    /**
     * Represent the Descriptor Factory Project Name
     */
    private String descriptorFactoryProjectName;

    /**
     * Represent the Type
     */
    private DescriptorFactoryProjectType type;

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
    private UUID publisherIdentityPublicKey;

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
    public InformationPublishedComponentImpl(){
        super();
        this.componentVersionDetailList = new ArrayList<>();
        this.screensShotsComponentList    = new ArrayList<>();
    }

    /**
     * Constructor with parameters
     *
     * @param componentVersionDetailList
     * @param descriptions
     * @param descriptorFactoryProjectId
     * @param descriptorFactoryProjectName
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
    public InformationPublishedComponentImpl(List<ComponentVersionDetail> componentVersionDetailList, String descriptions, UUID descriptorFactoryProjectId, String descriptorFactoryProjectName, Image iconImg, UUID id, Image mainScreenShotImg, Timestamp publicationTimestamp, UUID publisherIdentityPublicKey, List<Image> screensShotsComponentList, ComponentPublishedInformationStatus status, Timestamp statusTimestamp, DescriptorFactoryProjectType type, URL videoUrl) {
        super();
        this.componentVersionDetailList = componentVersionDetailList;
        this.descriptions = descriptions;
        this.descriptorFactoryProjectId = descriptorFactoryProjectId;
        this.descriptorFactoryProjectName = descriptorFactoryProjectName;
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
     * @see InformationPublishedComponentImpl#getComponentVersionDetailList()
     */
    @Override
    public List<ComponentVersionDetail> getComponentVersionDetailList() {
        return componentVersionDetailList;
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
     * @see InformationPublishedComponentImpl#getDescriptions()
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
     * @see InformationPublishedComponentImpl#getDescriptorFactoryProjectId()
     */
    @Override
    public UUID getDescriptorFactoryProjectId() {
        return descriptorFactoryProjectId;
    }


    /**
     * Set the descriptorFactoryProjectId
     *
     * @param descriptorFactoryProjectId
     */
    public void setDescriptorFactoryProjectId(UUID descriptorFactoryProjectId) {
        this.descriptorFactoryProjectId = descriptorFactoryProjectId;
    }

    /**
     * (non-Javadoc)
     * @see InformationPublishedComponentImpl#getDescriptorFactoryProjectName()
     */
    @Override
    public String getDescriptorFactoryProjectName() {
        return descriptorFactoryProjectName;
    }


    /**
     * Set the descriptorFactoryProjectName
     *
     * @param descriptorFactoryProjectName
     */
    public void setDescriptorFactoryProjectName(String descriptorFactoryProjectName) {
        this.descriptorFactoryProjectName = descriptorFactoryProjectName;
    }

    /**
     * (non-Javadoc)
     * @see InformationPublishedComponentImpl#getIconImg()
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
     * @see InformationPublishedComponentImpl#getId()
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
     * @see InformationPublishedComponentImpl#getMainScreenShotImg()
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
     * @see InformationPublishedComponentImpl#getPublicationTimestamp()
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
     * @see InformationPublishedComponentImpl#getPublisherIdentityPublicKey()
     */
    @Override
    public UUID getPublisherIdentityPublicKey() {
        return publisherIdentityPublicKey;
    }

    /**
     * Set the publisherIdentityPublicKey
     *
     * @param publisherIdentityPublicKey
     */
    public void setPublisherIdentityPublicKey(UUID publisherIdentityPublicKey) {
        this.publisherIdentityPublicKey = publisherIdentityPublicKey;
    }

    /**
     * (non-Javadoc)
     * @see InformationPublishedComponentImpl#getSignature()
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
     * @see InformationPublishedComponentImpl#getScreensShotsComponentList()
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
     * @see InformationPublishedComponentImpl#getStatus()
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
     * @see InformationPublishedComponentImpl#getStatusTimestamp()
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
     * @see InformationPublishedComponentImpl#getType()
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
     * @see InformationPublishedComponentImpl#getVideoUrl()
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
        if (!(o instanceof InformationPublishedComponentImpl)) return false;
        InformationPublishedComponentImpl that = (InformationPublishedComponentImpl) o;
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
        return "InformationPublishedComponentImpl{" +
                "componentVersionDetailList=" + componentVersionDetailList +
                ", id=" + id +
                ", descriptorFactoryProjectId=" + descriptorFactoryProjectId +
                ", descriptorFactoryProjectName='" + descriptorFactoryProjectName + '\'' +
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
