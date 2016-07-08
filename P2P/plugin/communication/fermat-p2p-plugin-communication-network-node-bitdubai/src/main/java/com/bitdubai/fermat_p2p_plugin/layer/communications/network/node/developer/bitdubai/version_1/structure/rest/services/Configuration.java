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

    private Integer port;
    private String user;
    private String password;
    private Boolean monitInstalled;
    private String monitUser;
    private String monitPassword;
    private String monitUrl;

    public Configuration() {
        super();
    }

    public Configuration(Integer port, String user, String password, Boolean monitInstalled, String monitUser, String monitPassword, String monitUrl) {
        this.port = port;
        this.user = user;
        this.password = password;
        this.monitInstalled = monitInstalled;
        this.monitUser = monitUser;
        this.monitPassword = monitPassword;
        this.monitUrl = monitUrl;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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
}
