/*
 * @#NetworkServiceProfile.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.profiles;

import com.google.gson.Gson;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.profiles.NetworkServiceProfile</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkServiceProfile extends Profile {

    /**
     * Represent the networkServiceType
     */
    private String networkServiceType;

    /**
     * Represent the clientIdentityPublicKey
     */
    private String clientIdentityPublicKey;

    /**
     * Constructor
     */
    public NetworkServiceProfile(){
        super();
    }

    /**
     * Gets the value of clientIdentityPublicKey and returns
     *
     * @return clientIdentityPublicKey
     */
    public String getClientIdentityPublicKey() {
        return clientIdentityPublicKey;
    }

    /**
     * Sets the clientIdentityPublicKey
     *
     * @param clientIdentityPublicKey to set
     */
    public void setClientIdentityPublicKey(String clientIdentityPublicKey) {
        this.clientIdentityPublicKey = clientIdentityPublicKey;
    }

    /**
     * Gets the value of networkServiceType and returns
     *
     * @return networkServiceType
     */
    public String getNetworkServiceType() {
        return networkServiceType;
    }

    /**
     * Sets the networkServiceType
     *
     * @param networkServiceType to set
     */
    public void setNetworkServiceType(String networkServiceType) {
        this.networkServiceType = networkServiceType;
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
    public NetworkServiceProfile fromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, this.getClass());
    }


}
