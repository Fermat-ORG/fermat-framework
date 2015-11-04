/*
 * @#KnownServerCatalogInfo.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.entities;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.entities.KnownServerCatalogInfo</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 22/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class KnownServerCatalogInfo {

    /**
     * Represent the identityPublicKey
     */
    private String identityPublicKey;

    /**
     * Represent the name
     */
    private String name;

    /**
     * Represent the ip
     */
    private String ip;

    /**
     * Represent the defaultPort
     */
    private Integer defaultPort;

    /**
     * Represent the webServicePort
     */
    private Integer webServicePort;

    /**
     * Represent the latitude
     */
    private Double latitude;

    /**
     * Represent the longitude
     */
    private Double longitude;

    /**
     * Represent the lateNotificationCounter
     */
    private Integer lateNotificationCounter;

    /**
     * Represent the offlineCounter
     */
    private Integer offlineCounter;

    /**
     * Represent the registeredTimestamp
     */
    private Timestamp registeredTimestamp;

    /**
     * Represent the lastConnectionTimestamp
     */
    private Timestamp lastConnectionTimestamp;

    /**
     * Constructor
     */
    public KnownServerCatalogInfo() {
        super();
    }

    /**
     * Constructor whit parameters
     *
     * @param defaultPort
     * @param identityPublicKey
     * @param ip
     * @param lastConnectionTimestamp
     * @param lateNotificationCounter
     * @param latitude
     * @param longitude
     * @param name
     * @param offlineCounter
     * @param registeredTimestamp
     * @param webServicePort
     */
    public KnownServerCatalogInfo(Integer defaultPort, String identityPublicKey, String ip, Timestamp lastConnectionTimestamp, Integer lateNotificationCounter, Double latitude, Double longitude, String name, Integer offlineCounter, Timestamp registeredTimestamp, Integer webServicePort) {
        this.defaultPort = defaultPort;
        this.identityPublicKey = identityPublicKey;
        this.ip = ip;
        this.lastConnectionTimestamp = lastConnectionTimestamp;
        this.lateNotificationCounter = lateNotificationCounter;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.offlineCounter = offlineCounter;
        this.registeredTimestamp = registeredTimestamp;
        this.webServicePort = webServicePort;
    }

    /**
     * Get the DefaultPort
     * @return Integer
     */
    public Integer getDefaultPort() {
        return defaultPort;
    }

    /**
     * Set the DefaultPort
     * @param defaultPort
     */
    public void setDefaultPort(Integer defaultPort) {
        this.defaultPort = defaultPort;
    }

    /**
     * Get the IdentityPublicFey
     * @return String
     */
    public String getIdentityPublicKey() {
        return identityPublicKey;
    }

    /**
     * Set the IdentityPublicFey
     * @param identityPublicKey
     */
    public void setIdentityPublicKey(String identityPublicKey) {
        this.identityPublicKey = identityPublicKey;
    }

    /**
     * Get the Ip
     * @return String
     */
    public String getIp() {
        return ip;
    }

    /**
     * Set the Ip
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Get the LastConnectionTimestamp
     * @return String
     */
    public Timestamp getLastConnectionTimestamp() {
        return lastConnectionTimestamp;
    }

    /**
     * Set the LastConnectionTimestamp
     * @param lastConnectionTimestamp
     */
    public void setLastConnectionTimestamp(Timestamp lastConnectionTimestamp) {
        this.lastConnectionTimestamp = lastConnectionTimestamp;
    }

    /**
     * Get the LateNotificationCounter
     * @return Integer
     */
    public Integer getLateNotificationCounter() {
        return lateNotificationCounter;
    }

    /**
     * Set the LateNotificationCounter
     * @param lateNotificationCounter
     */
    public void setLateNotificationCounter(Integer lateNotificationCounter) {
        this.lateNotificationCounter = lateNotificationCounter;
    }

    /**
     * Get the Latitude
     * @return Double
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Set the Latitude
     * @param latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Get the Longitude
     * @return Double
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Set the Longitude
     * @param longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Get the Name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Set the Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the OfflineCounter
     * @return Integer
     */
    public Integer getOfflineCounter() {
        return offlineCounter;
    }

    /**
     * Set the OfflineCounter
     * @param offlineCounter
     */
    public void setOfflineCounter(Integer offlineCounter) {
        this.offlineCounter = offlineCounter;
    }

    /**
     * Get the RegisteredTimestamp
     * @return Timestamp
     */
    public Timestamp getRegisteredTimestamp() {
        return registeredTimestamp;
    }

    /**
     * Set th RegisteredTimestamp
     * @param registeredTimestamp
     */
    public void setRegisteredTimestamp(Timestamp registeredTimestamp) {
        this.registeredTimestamp = registeredTimestamp;
    }

    /**
     * Get the WebServicePort
     * @return Integer
     */
    public Integer getWebServicePort() {
        return webServicePort;
    }

    /**
     * Set the WebServicePort
     * @param webServicePort
     */
    public void setWebServicePort(Integer webServicePort) {
        this.webServicePort = webServicePort;
    }

    /**
     * (non-javadoc)
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KnownServerCatalogInfo)) return false;
        KnownServerCatalogInfo that = (KnownServerCatalogInfo) o;
        return Objects.equals(getIdentityPublicKey(), that.getIdentityPublicKey()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getIp(), that.getIp()) &&
                Objects.equals(getDefaultPort(), that.getDefaultPort()) &&
                Objects.equals(getWebServicePort(), that.getWebServicePort()) &&
                Objects.equals(getLatitude(), that.getLatitude()) &&
                Objects.equals(getLongitude(), that.getLongitude()) &&
                Objects.equals(getLateNotificationCounter(), that.getLateNotificationCounter()) &&
                Objects.equals(getOfflineCounter(), that.getOfflineCounter()) &&
                Objects.equals(getRegisteredTimestamp(), that.getRegisteredTimestamp()) &&
                Objects.equals(getLastConnectionTimestamp(), that.getLastConnectionTimestamp());
    }

    /**
     * (non-javadoc)
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(getIdentityPublicKey(), getName(), getIp(), getDefaultPort(), getWebServicePort(), getLatitude(), getLongitude(), getLateNotificationCounter(), getOfflineCounter(), getRegisteredTimestamp(), getLastConnectionTimestamp());
    }

    /**
     * (non-javadoc)
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "KnownServerCatalogInfo{" +
                "defaultPort=" + defaultPort +
                ", identityPublicKey='" + identityPublicKey + '\'' +
                ", name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", webServicePort=" + webServicePort +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", lateNotificationCounter=" + lateNotificationCounter +
                ", offlineCounter=" + offlineCounter +
                ", registeredTimestamp='" + registeredTimestamp + '\'' +
                ", lastConnectionTimestamp='" + lastConnectionTimestamp + '\'' +
                '}';
    }
}
