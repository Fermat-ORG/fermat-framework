/*
 * @#NetworkServiceConnectionManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.dmp_network_service;

import com.bitdubai.fermat_api.layer.all_definition.components.PlatformComponentProfile;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_network_service.NetworkServiceConnectionManager</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 28/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface NetworkServiceConnectionManager {

    /**
     * Create a new connection to
     *
     * @param remotePlatformComponentProfile the remote PlatformComponentProfile
     */
    public void connectTo(PlatformComponentProfile remotePlatformComponentProfile);


    /**
     * Close a previous connection
     *
     * @param remoteNetworkServicePublicKey he remote network service public key
     */
    public void closeConnection(String remoteNetworkServicePublicKey);


    /**
     * Close all previous connections
     */
    public void closeAllConnection();


    /**
     * Return the NetworkServiceLocal that represent the intra user network service remote
     *
     * @param remoteNetworkServicePublicKey the remote network service public key
     * @return NetworkServiceLocal the local instance that represent
     */
    public NetworkServiceLocal getNetworkServiceLocalInstance(String remoteNetworkServicePublicKey);
}
