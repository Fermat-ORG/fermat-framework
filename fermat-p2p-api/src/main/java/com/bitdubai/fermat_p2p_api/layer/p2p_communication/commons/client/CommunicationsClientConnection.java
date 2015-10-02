/*
 * @#CommunicationsClientConnection.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client;

import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.DiscoveryQueryParameters;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;


/**
 * The interface <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection</code> represent
 * a connection client
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface CommunicationsClientConnection {

    /**
     * Construct a PlatformComponentProfile instance, for use in the process
     * of registering with the cloud client components
     *
     * @param identityPublicKey
     * @param alias
     * @param name
     * @param networkServiceType
     * @param platformComponentType
     *
     * @return PlatformComponentProfile
     */
    public PlatformComponentProfile constructPlatformComponentProfileFactory(String identityPublicKey, String alias, String name, NetworkServiceType networkServiceType, PlatformComponentType platformComponentType, String extraData);

    /**
     * Construct a DiscoveryQueryParameters instance, for use in the process
     * of the discovery query to search all component register in the communication
     * cloud server that match with the params
     *
     * @param applicant
     * @param alias
     * @param identityPublicKey
     * @param location
     * @param distance
     * @param name
     * @param extraData
     * @param firstRecord
     * @param numRegister
     * @param fromOtherPlatformComponentType
     * @param fromOtherNetworkServiceType
     * @return DiscoveryQueryParameters
     */
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentProfile applicant, String alias, String identityPublicKey, Location location, Double distance, String name, String extraData, Integer firstRecord, Integer numRegister, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType);

    /**
     * Method that register a platform component with for Communication like online
     *
     * @param platformComponentProfile
     */
    public void registerComponentForCommunication(PlatformComponentProfile platformComponentProfile);

    /**
     * Method that request to the communication cloud server the list of component registered that mathc
     * whit the discovery query params
     *
     * @param discoveryQueryParameters
     */
    public void requestListComponentRegistered(DiscoveryQueryParameters discoveryQueryParameters);

    /**
     * Method that request to the communication cloud server create a vpn connection between the applicant and
     * the remote destination component to send message
     *
     * @param applicant who is made the request
     * @param remoteDestination the remote destination component to receive message
     */
    public void requestVpnConnection(PlatformComponentProfile applicant, PlatformComponentProfile remoteDestination);

    /**
     * Method that verified is the connection is
     * connected whit the server
     *
     * @return boolean
     */
    public boolean isConnected();

    /**
     * Get the isActive value
     * @return boolean
     */
    public boolean isRegister();

    /**
     * Get the CommunicationsVPNConnection stablished
     *
     * @param applicant
     * @return CommunicationsVPNConnection
     */
    public CommunicationsVPNConnection getCommunicationsVPNConnectionStablished(PlatformComponentProfile applicant, String remotePlatformComponentProfile);

}
