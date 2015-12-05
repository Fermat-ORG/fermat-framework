/*
 * @#NetworkServiceProfile.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.profiles;

import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.google.gson.Gson;

/**
 * The Class <code>NetworkServiceProfile</code>
 * define the profile of the network service component
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
    private NetworkServiceType networkServiceType;

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
    public NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    /**
     * Sets the networkServiceType
     *
     * @param networkServiceType to set
     */
    public void setNetworkServiceType(NetworkServiceType networkServiceType) {
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
