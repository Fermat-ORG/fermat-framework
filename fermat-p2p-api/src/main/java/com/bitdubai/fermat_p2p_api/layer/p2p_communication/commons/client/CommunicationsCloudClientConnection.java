/*
 * @#CommunicationsCloudClientChannelManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client;

import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.components.PlatformComponentProfile;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.NetworkServiceType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.enums.PlatformComponentType;

/**
 * The interface <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsCloudClientConnection</code> represent
 * a connection with the server
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface CommunicationsCloudClientConnection {

    /**
     * Construct a PlatformComponentProfile instance, for use in the process
     * of registering with the cloud client components
     *
     * @param identityPublicKey
     * @param alias
     * @param latitude
     * @param longitude
     * @param name
     * @param networkServiceType
     * @param platformComponentType
     *
     * @return PlatformComponentProfile
     */
    public PlatformComponentProfile constructPlatformComponentProfileFactory(String identityPublicKey, String alias, Double latitude, Double longitude, String name, NetworkServiceType networkServiceType, PlatformComponentType platformComponentType);

    /**
     * Method that register a platform component with the Communication Cloud Server
     * like online
     *
     * @param platformComponentProfile
     */
    public void registerComponentInCommunicationCloudServer(PlatformComponentProfile platformComponentProfile);

    /**
     * Method that request to the communication cloud server the list of component registered.
     *
     * @param requestedPlatformComponentProfile
     */
    public void requestListComponentRegistered(PlatformComponentProfile requestedPlatformComponentProfile);

    /**
     * Method that send a message to other component
     *
     * @param fermatMessage
     */
    public void sendMessage(FermatMessage fermatMessage);

    /**
     * Method that verified is the connection is
     * connected whit the server
     *
     * @return boolean
     */
    public boolean isConnected();

}
