/*
 * @#PlatformComponentProfileCommunication.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.PlatformComponentType;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.PlatformComponentProfileCommunication</code> represent
 * the platform component profile.
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 04/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PlatformComponentProfileCommunication implements PlatformComponentProfile, Serializable {

    /**
     * Represent the serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represent the identityPublicKey
     */
    private String identityPublicKey;

    /**
     * Represent the alias
     */
    private String alias;

    /**
     * Represent the name
     */
    private String name;

    /**
     * Represent the latitude
     */
    private Double latitude;

    /**
     * Represent the longitude
     */
    private Double longitude;

    /**
     * Represent the platformComponentType
     */
    private PlatformComponentType platformComponentType;

    /**
     * Represent the networkServiceType
     */
    private NetworkServiceType networkServiceType;

    /**
     * Represent the communicationCloudClientIdentity
     */
    private String communicationCloudClientIdentity;

    /**
     * Represent the extraData
     */
    private String extraData;

    /**
     * Constructor
     */
    public PlatformComponentProfileCommunication() {
        super();
        this.alias = null;
        this.communicationCloudClientIdentity = null;
        this.identityPublicKey = null;
        this.latitude = null;
        this.longitude = null;
        this.name = null;
        this.networkServiceType = null;
        this.platformComponentType = null;
        this.extraData = null;
    }

    /**
     * Constructor whit parameters
     *
     * @param alias
     * @param communicationCloudClientIdentity
     * @param identityPublicKey
     * @param latitude
     * @param longitude
     * @param name
     * @param networkServiceType
     * @param platformComponentType
     */
    public PlatformComponentProfileCommunication(String alias, String communicationCloudClientIdentity, String identityPublicKey, Double latitude, Double longitude, String name, NetworkServiceType networkServiceType, PlatformComponentType platformComponentType, String extraData) {
        super();
        this.alias = alias;
        this.communicationCloudClientIdentity = communicationCloudClientIdentity;
        this.identityPublicKey = identityPublicKey;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.networkServiceType = networkServiceType;
        this.platformComponentType = platformComponentType;
        this.extraData = extraData;
    }

    /**
     * (non-javadoc)
     * @see PlatformComponentProfile#getIdentityPublicKey()
     */
    @Override
    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

    /**
     * (non-javadoc)
     * @see PlatformComponentProfile#getAlias()
     */
    @Override
    public String getAlias() {
        return alias;
    }

    /**
     * (non-javadoc)
     * @see PlatformComponentProfile#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * (non-javadoc)
     * @see PlatformComponentProfile#getLatitude()
     */
    @Override
    public Double getLatitude() {
        return latitude;
    }

    /**
     * (non-javadoc)
     * @see PlatformComponentProfile#getLongitude()
     */
    @Override
    public Double getLongitude() {
        return longitude;
    }

    /**
     * (non-javadoc)
     * @see PlatformComponentProfile#getPlatformComponentType()
     */
    @Override
    public PlatformComponentType getPlatformComponentType() {
        return platformComponentType;
    }

    /**
     * (non-javadoc)
     * @see PlatformComponentProfile#getNetworkServiceType()
     */
    @Override
    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    /**
     * (non-javadoc)
     * @see PlatformComponentProfile#getCommunicationCloudClientIdentity()
     */
    @Override
    public String getCommunicationCloudClientIdentity() {
        return communicationCloudClientIdentity;
    }

    /**
     * (non-javadoc)
     * @see PlatformComponentProfile#getExtraData()
     */
    @Override
    public String getExtraData() {
        return extraData;
    }

    /**
     * (non-javadoc)
     * @see PlatformComponentProfile#toJson()
     */
    @Override
    public String toJson() {

        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * (non-javadoc)
     * @see PlatformComponentProfile#fromJson(String)
     */
    @Override
    public PlatformComponentProfile fromJson(String json) {

        Gson gson = new Gson();
        return gson.fromJson(json, PlatformComponentProfileCommunication.class);
    }

    /**
     * (non-javadoc)
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlatformComponentProfileCommunication)) return false;
        PlatformComponentProfileCommunication that = (PlatformComponentProfileCommunication) o;
        return Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getAlias(), that.getAlias()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getLatitude(), that.getLatitude()) &&
                Objects.equals(getLongitude(), that.getLongitude()) &&
                Objects.equals(getPlatformComponentType(), that.getPlatformComponentType()) &&
                Objects.equals(getNetworkServiceType(), that.getNetworkServiceType()) &&
                Objects.equals(getCommunicationCloudClientIdentity(), that.getCommunicationCloudClientIdentity()) &&
                Objects.equals(getExtraData(), that.getExtraData());
    }

    /**
     * (non-javadoc)
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(getIdentityPublicKey(), getAlias(), getName(), getLatitude(), getLongitude(), getPlatformComponentType(), getNetworkServiceType(), getCommunicationCloudClientIdentity(), getExtraData());
    }

    /**
     * (non-javadoc)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "PlatformComponentProfileCommunication{" +
                "alias='" + alias + '\'' +
                ", identityPublicKey='" + identityPublicKey + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", platformComponentType=" + platformComponentType +
                ", networkServiceType=" + networkServiceType +
                ", communicationCloudClientIdentity='" + communicationCloudClientIdentity + '\'' +
                ", extraData() "+extraData + '}';
    }
}
