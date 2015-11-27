/*
* @#ClientConnectionHistory.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.database.entities;

import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.structure.database.entities.ClientConnectionHistory</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 13/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientConnectionHistory {

    /**
     * Represent the identityPublicKey
     */
    private String identityPublicKey;

    /**
     * Represent the name
     */
    private String name;

    /**
     * Represent the alias
     */
    private String alias;

    /**
     * Represent the component Type
     */
    private String componentType;

    /**
     * Represent the network Service Type
     */
    private String networkServiceType;

    /**
     * Represent the latitude
     */
    private Double lastLatitude;

    /**
     * Represent the longitude
     */
    private Double lastLongitude;

    /**
     * Represent the extraData
     */
    private String extraData;

    /**
     * Represent the lastConnectionTimestamp
     */
    private Timestamp lastConnectionTimestamp;


    public ClientConnectionHistory(){super();}


    public ClientConnectionHistory(String identityPublicKey,
                                   String name,
                                   String alias,
                                   String componentType,
                                   String networkServiceType,
                                   Double lastLatitude,
                                   Double lastLongitude,
                                   String extraData,
                                   Timestamp lastConnectionTimestamp){

        this.identityPublicKey=identityPublicKey;
        this.name=name;
        this.alias=alias;
        this.componentType=componentType;
        this.networkServiceType=networkServiceType;
        this.lastLatitude=lastLatitude;
        this.lastLongitude=lastLongitude;
        this.extraData=extraData;
        this.lastConnectionTimestamp=lastConnectionTimestamp;
    }

    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

    public void setIdentityPublicKey(String identityPublicKey) {
        this.identityPublicKey = identityPublicKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getNetworkServiceType() {
        return networkServiceType;
    }

    public void setNetworkServiceType(String networkServiceType) {
        this.networkServiceType = networkServiceType;
    }

    public Double getLastLatitude() {
        return lastLatitude;
    }

    public void setLastLatitude(Double lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    public Double getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(Double lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public Timestamp getLastConnectionTimestamp() {
        return lastConnectionTimestamp;
    }

    public void setLastConnectionTimestamp(Timestamp lastConnectionTimestamp) {
        this.lastConnectionTimestamp = lastConnectionTimestamp;
    }
}
