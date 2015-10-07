/*
 * @#DiscoveryQueryParametersCommunication.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components.DiscoveryQueryParametersCommunication</code> is the implementation
 * of the <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.DiscoveryQueryParameters</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 16/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DiscoveryQueryParametersCommunication implements DiscoveryQueryParameters, Serializable {

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
     * Represent the location
     */
    private DeviceLocation location;

    /**
     * Represent the distance
     */
    private Double distance;

    /**
     * Represent the platformComponentType
     */
    private PlatformComponentType platformComponentType;

    /**
     * Represent the networkServiceType
     */
    private NetworkServiceType networkServiceType;

    /**
     * Represent the extraData
     */
    private String extraData;

    /**
     * Represent the offset
     */
    private Integer offset;

    /**
     * Represent the max
     */
    private Integer max;

    /**
     * Represent the fromOtherPlatformComponentType
     */
    private PlatformComponentType fromOtherPlatformComponentType;

    /**
     * Represent the fromOtherNetworkServiceType
     */
    private NetworkServiceType fromOtherNetworkServiceType;


    /**
     * Constructor
     */
    public DiscoveryQueryParametersCommunication() {
        super();
        this.alias = null;
        this.identityPublicKey = null;
        this.location = null;
        this.distance = null;
        this.name = null;
        this.networkServiceType = null;
        this.platformComponentType = null;
        this.extraData = null;
        this.offset = new Integer(0);
        this.max = new Integer(0);
        this.fromOtherPlatformComponentType = null;
        this.fromOtherNetworkServiceType = null;
    }

    /**
     * Constructor with parameters
     *
     * @param alias
     * @param identityPublicKey
     * @param location
     * @param distance
     * @param name
     * @param networkServiceType
     * @param platformComponentType
     * @param extraData
     * @param offset
     * @param max
     * @param fromOtherPlatformComponentType
     * @param fromOtherNetworkServiceType
     */
    public DiscoveryQueryParametersCommunication(String alias, String identityPublicKey, Location location, Double distance, String name, NetworkServiceType networkServiceType, PlatformComponentType platformComponentType, String extraData, Integer offset, Integer max, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType) {
        super();
        this.alias = alias;
        this.identityPublicKey = identityPublicKey;
        this.location = (DeviceLocation) location;
        this.distance = distance;
        this.name = name;
        this.networkServiceType = networkServiceType;
        this.platformComponentType = platformComponentType;
        this.extraData = extraData;
        this.offset = offset;
        this.max = max;
        this.fromOtherPlatformComponentType = fromOtherPlatformComponentType;
        this.fromOtherNetworkServiceType = fromOtherNetworkServiceType;
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#getIdentityPublicKey()
     */
    @Override
    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#getAlias()
     */
    @Override
    public String getAlias() {
        return alias;
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#getLocation()
     */
    @Override
    public Location getLocation() {
        return location;
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#getDistance()
     */
    @Override
    public Double getDistance() {
        return distance;
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#getPlatformComponentType()
     */
    @Override
    public PlatformComponentType getPlatformComponentType() {
        return platformComponentType;
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#getNetworkServiceType()
     */
    @Override
    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#getExtraData()
     */
    @Override
    public String getExtraData() {
        return extraData;
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#getOffset()
     */
    public Integer getOffset(){
        return offset;
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#getMax()
     */
    public Integer getMax(){
        return max;
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#getFromOtherPlatformComponentType()
     */
    @Override
    public PlatformComponentType getFromOtherPlatformComponentType() {
        return fromOtherPlatformComponentType;
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#getFromOtherNetworkServiceType()
     */
    @Override
    public NetworkServiceType getFromOtherNetworkServiceType() {
        return fromOtherNetworkServiceType;
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#toJson()
     */
    @Override
    public String toJson() {

        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#fromJson(String)
     */
    @Override
    public DiscoveryQueryParametersCommunication fromJson(String json) {

        Gson gson = new Gson();
        return gson.fromJson(json, DiscoveryQueryParametersCommunication.class);
    }

    /**
     * (non-javadoc)
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscoveryQueryParametersCommunication)) return false;
        DiscoveryQueryParametersCommunication that = (DiscoveryQueryParametersCommunication) o;
        return Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getAlias(), that.getAlias()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getLocation(), that.getLocation()) &&
                Objects.equals(getDistance(), that.getDistance()) &&
                Objects.equals(getPlatformComponentType(), that.getPlatformComponentType()) &&
                Objects.equals(getNetworkServiceType(), that.getNetworkServiceType()) &&
                Objects.equals(getExtraData(), that.getExtraData()) &&
                Objects.equals(offset, that.offset) &&
                Objects.equals(getMax(), that.getMax()) &&
                Objects.equals(getFromOtherPlatformComponentType(), that.getFromOtherPlatformComponentType()) &&
                Objects.equals(getFromOtherNetworkServiceType(), that.getFromOtherNetworkServiceType());
    }

    /**
     * (non-javadoc)
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(getIdentityPublicKey(), getAlias(), getName(), getLocation(), getDistance(), getPlatformComponentType(), getNetworkServiceType(), getExtraData(), offset, getMax(), getFromOtherPlatformComponentType(), getFromOtherNetworkServiceType());
    }

    /**
     * (non-javadoc)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "DiscoveryQueryParametersCommunication{" +
                "alias='" + alias + '\'' +
                ", identityPublicKey='" + identityPublicKey + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", distance=" + distance +
                ", platformComponentType=" + platformComponentType +
                ", networkServiceType=" + networkServiceType +
                ", extraData='" + extraData + '\'' +
                ", offset=" + offset +
                ", max=" + max +
                ", fromOtherPlatformComponentType=" + fromOtherPlatformComponentType +
                ", fromOtherNetworkServiceType=" + fromOtherNetworkServiceType +
                '}';
    }

}
