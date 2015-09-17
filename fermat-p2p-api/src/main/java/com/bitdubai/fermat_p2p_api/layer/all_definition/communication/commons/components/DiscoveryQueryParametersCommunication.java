/*
 * @#DiscoveryQueryParametersCommunication.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.components;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.DiscoveryQueryParameters;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.PlatformComponentType;
import com.google.gson.Gson;

import java.io.Serializable;

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
        this.latitude = null;
        this.longitude = null;
        this.name = null;
        this.networkServiceType = null;
        this.platformComponentType = null;
        this.extraData = null;
        this.firstRecord = null;
        this.numberRegister = null;
    }

    /**
     * Constructor whit parameters
     *
     * @param alias
     * @param identityPublicKey
     * @param latitude
     * @param longitude
     * @param name
     * @param networkServiceType
     * @param platformComponentType
     */
    public DiscoveryQueryParametersCommunication(String alias, String identityPublicKey, Double latitude, Double longitude, String name, NetworkServiceType networkServiceType, PlatformComponentType platformComponentType, String extraData, Integer firstRecord, Integer numberRegister) {
        super();
        this.alias = alias;
        this.identityPublicKey = identityPublicKey;
        this.latitude = latitude;
        this.longitude = longitude;
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
     * @see DiscoveryQueryParameters#getLatitude()
     */
    @Override
    public Double getLatitude() {
        return latitude;
    }

    /**
     * (non-javadoc)
     * @see DiscoveryQueryParameters#getLongitude()
     */
    @Override
    public Double getLongitude() {
        return longitude;
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
}
