/*
 * @#Configuration  - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices.Configuration</code> implements
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 25/02/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@XmlRootElement
public class Configuration {

    private String ipk;
    private String nodeName;
    private String internalIp;
    private String publicIp;
    private Integer port;
    private Double latitude;
    private Double longitude;
    private String user;
    private String password;
    private Boolean monitInstalled;
    private String monitUser;
    private String monitPassword;
    private String monitUrl;
    private Boolean registerInCatalog;
    private String lastRegisterNodeProfile;
    private String googleMapApiKey;

    public Configuration() {
        super();
    }

    public Configuration(String ipk, String nodeName, String internalIp, String publicIp, Integer port, Double latitude, Double longitude, String user, String password, Boolean monitInstalled, String monitUser, String monitPassword, String monitUrl, Boolean registerInCatalog, String lastRegisterNodeProfile, String googleMapApiKey) {
        this.ipk = ipk;
        this.nodeName = nodeName;
        this.internalIp = internalIp;
        this.publicIp = publicIp;
        this.port = port;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
        this.password = password;
        this.monitInstalled = monitInstalled;
        this.monitUser = monitUser;
        this.monitPassword = monitPassword;
        this.monitUrl = monitUrl;
        this.registerInCatalog = registerInCatalog;
        this.lastRegisterNodeProfile = lastRegisterNodeProfile;
        this.googleMapApiKey = googleMapApiKey;
    }

    public String getIpk() {
        return ipk;
    }

    public void setIpk(String ipk) {
        this.ipk = ipk;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getInternalIp() {
        return internalIp;
    }

    public void setInternalIp(String internalIp) {
        this.internalIp = internalIp;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getMonitInstalled() {
        return monitInstalled;
    }

    public void setMonitInstalled(Boolean monitInstalled) {
        this.monitInstalled = monitInstalled;
    }

    public String getMonitUser() {
        return monitUser;
    }

    public void setMonitUser(String monitUser) {
        this.monitUser = monitUser;
    }

    public String getMonitPassword() {
        return monitPassword;
    }

    public void setMonitPassword(String monitPassword) {
        this.monitPassword = monitPassword;
    }

    public String getMonitUrl() {
        return monitUrl;
    }

    public void setMonitUrl(String monitUrl) {
        this.monitUrl = monitUrl;
    }

    public Boolean getRegisterInCatalog() {
        return registerInCatalog;
    }

    public void setRegisterInCatalog(Boolean registerInCatalog) {
        this.registerInCatalog = registerInCatalog;
    }

    public String getLastRegisterNodeProfile() {
        return lastRegisterNodeProfile;
    }

    public void setLastRegisterNodeProfile(String lastRegisterNodeProfile) {
        this.lastRegisterNodeProfile = lastRegisterNodeProfile;
    }

    public String getGoogleMapApiKey() {
        return googleMapApiKey;
    }

    public void setGoogleMapApiKey(String googleMapApiKey) {
        this.googleMapApiKey = googleMapApiKey;
    }
}
