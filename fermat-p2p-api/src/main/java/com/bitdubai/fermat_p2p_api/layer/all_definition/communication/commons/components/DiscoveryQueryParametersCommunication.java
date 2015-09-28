/*
 * @#DiscoveryQueryParametersCommunication.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components;

import com.bitdubai.fermat_api.layer.all_definition.components.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponentType;
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
     * Represent the firstRecord
     */
    private Integer firstRecord;

    /**
     * Represent the numberRegister
     */
    private Integer numberRegister;


    /**
     * Constructor
     */
    public DiscoveryQueryParametersCommunication() {
        super();
        this.alias = null;
        this.identityPublicKey = null;
        this.location = null;
        this.name = null;
        this.networkServiceType = null;
        this.platformComponentType = null;
        this.extraData = null;
        this.firstRecord  = new Integer(0);
        this.numberRegister = new Integer(0);
    }

    /**
     * Constructor whit parameters
     *
     * @param alias
     * @param identityPublicKey
     * @param location
     * @param name
     * @param networkServiceType
     * @param platformComponentType
     */
    public DiscoveryQueryParametersCommunication(String alias, String identityPublicKey, Location location, String name, NetworkServiceType networkServiceType, PlatformComponentType platformComponentType, String extraData, Integer firstRecord, Integer numberRegister) {
        super();
        this.alias = alias;
        this.identityPublicKey = identityPublicKey;
        this.location = (DeviceLocation) location;
        this.name = name;
        this.networkServiceType = networkServiceType;
        this.platformComponentType = platformComponentType;
        this.extraData = extraData;
        this.firstRecord = firstRecord;
        this.numberRegister = numberRegister;
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
     * @see DiscoveryQueryParameters#firstRecord()
     */
    public Integer firstRecord(){
        return firstRecord;
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#getNumberRegister()
     */
    public Integer getNumberRegister(){
        return numberRegister;
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
                Objects.equals(getPlatformComponentType(), that.getPlatformComponentType()) &&
                Objects.equals(getNetworkServiceType(), that.getNetworkServiceType()) &&
                Objects.equals(getExtraData(), that.getExtraData()) &&
                Objects.equals(firstRecord, that.firstRecord) &&
                Objects.equals(getNumberRegister(), that.getNumberRegister());
    }

    /**
     * (non-javadoc)
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(getIdentityPublicKey(), getAlias(), getName(), getLocation(), getPlatformComponentType(), getNetworkServiceType(), getExtraData(), firstRecord, getNumberRegister());
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
                ", platformComponentType=" + platformComponentType +
                ", networkServiceType=" + networkServiceType +
                ", extraData='" + extraData + '\'' +
                ", firstRecord=" + firstRecord +
                ", numberRegister=" + numberRegister +
                '}';
    }
}
