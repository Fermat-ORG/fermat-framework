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
     * @param platformComponentType
     * @param networkServiceType
     * @param alias
     * @param identityPublicKey
     * @param location
     * @param distance
     * @param name
     * @param extraData
     * @param offset
     * @param max
     * @param fromOtherPlatformComponentType
     * @param fromOtherNetworkServiceType
     * @return DiscoveryQueryParameters
     */
    public DiscoveryQueryParameters constructDiscoveryQueryParamsFactory(PlatformComponentType platformComponentType, NetworkServiceType networkServiceType, String alias, String identityPublicKey, Location location, Double distance, String name, String extraData, Integer offset, Integer max, PlatformComponentType fromOtherPlatformComponentType, NetworkServiceType fromOtherNetworkServiceType);

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
     * Method that request to the communication cloud server create a vpn connection between the applicant and
     * the remote destination component to send message, but the applicant only now a other component type identity public key
     * and the server has to discovery the component type for this identity public key that is the same type as the applicant.
     *
     * @param identityPublicKeyRequestingParticipant the identity public key of the participant of the vpn
     * @param applicantNetworkService the profile of the network service which it makes the request
     * @param discoveryQueryParameters have the other type component information to find the corresponding
     */
    public void requestDiscoveryVpnConnection(String identityPublicKeyRequestingParticipant, PlatformComponentProfile applicantNetworkService, DiscoveryQueryParameters discoveryQueryParameters);

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
