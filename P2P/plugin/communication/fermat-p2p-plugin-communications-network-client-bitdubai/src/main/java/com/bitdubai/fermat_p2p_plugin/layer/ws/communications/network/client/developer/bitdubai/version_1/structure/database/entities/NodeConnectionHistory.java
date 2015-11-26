/*
* @#NodeConnectionHistory.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1.structure.database.entities;

import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1.structure.database.entities.NodeConnectionHistory</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 13/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NodeConnectionHistory {

    /**
     * Represent the identityPublicKey
     */
    private String identityPublicKey;

    /**
     * Represent the ip
     */
    private String ip;

    /**
     * Represent the defaultPort
     */
    private Integer defaultPort;

    /**
     * Represent the latitude
     */
    private Double latitude;

    /**
     * Represent the longitude
     */
    private Double longitude;

    /**
     * Represent the lastConnectionTimestamp
     */
    private Timestamp lastConnectionTimestamp;

    public NodeConnectionHistory(){super();}

    public NodeConnectionHistory( String identityPublicKey, String ip, Integer defaultPort,  Double latitude, Double longitude, Timestamp lastConnectionTimestamp) {
        this.identityPublicKey = identityPublicKey;
        this.ip = ip;
        this.defaultPort=defaultPort;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastConnectionTimestamp = lastConnectionTimestamp;
    }


    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

    public void setIdentityPublicKey(String identityPublicKey) {
        this.identityPublicKey = identityPublicKey;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getDefaultPort() {
        return defaultPort;
    }

    public void setDefaultPort(Integer defaultPort) {
        this.defaultPort = defaultPort;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Timestamp getLastConnectionTimestamp() {
        return lastConnectionTimestamp;
    }

    public void setLastConnectionTimestamp(Timestamp lastConnectionTimestamp) {
        this.lastConnectionTimestamp = lastConnectionTimestamp;
    }




}
