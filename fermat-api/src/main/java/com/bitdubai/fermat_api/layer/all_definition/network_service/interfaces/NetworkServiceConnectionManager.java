/*
 * @#NetworkServiceConnectionManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkServiceConnectionManager</code>
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
     * Create a new connection to from another platform component type, it is mean tha the who made the request is not a network service
     *
     * NOTE: the discoveryQueryParameters param has to be set the following attribute set
     *
     * IdentityPublicKey of the other platform component type who made the request
     * FromOtherPlatformComponentType of the other platform component type who made the request
     * FromOtherNetworkServiceType of the other platform component type who made the request
     *
     * All three attribute are required
     *
     * @param identityPublicKeyRequestingParticipant the identity public key of the participant of the vpn
     * @param applicantNetworkService the profile of the network service which it makes the request
     * @param discoveryQueryParameters have the other type component information to find the corresponding
     */
    public void connectTo(String identityPublicKeyRequestingParticipant, PlatformComponentProfile applicantNetworkService, DiscoveryQueryParameters discoveryQueryParameters);


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
