/*
 * @#NodeProfile.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles;

import com.google.gson.Gson;

import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles.NodeProfile</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 26/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NodeProfile extends Profile {

    /**
     * Represent the defaultPort
     */
    private Integer defaultPort;

    /**
     * Represent the ip
     */
    private String ip;

    /**
     * Represent the name
     */
    private String name;

    /**
     * Constructor
     */
    public NodeProfile(){
        super();
    }

    /**
     * Gets the value of defaultPort and returns
     *
     * @return defaultPort
     */
    public Integer getDefaultPort() {
        return defaultPort;
    }

    /**
     * Sets the defaultPort
     *
     * @param defaultPort to set
     */
    public void setDefaultPort(Integer defaultPort) {
        this.defaultPort = defaultPort;
    }

    /**
     * Gets the value of ip and returns
     *
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * Sets the ip
     *
     * @param ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Gets the value of name and returns
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     *
     * @param name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * (no-javadoc)
     * @see Profile#toJson()
     */
    @Override
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * (no-javadoc)
     * @see Profile#fromJson(String)
     */
    @Override
    public NodeProfile fromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, this.getClass());
    }


}
