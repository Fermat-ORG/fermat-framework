package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.enums.NetworkServices;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud.enums.RejectConnectionRequestReasons;

import java.util.Collection;

/**
 * Created by ciencias on 2/22/15.
 */
public interface CommunicationLayerManager {
    
    public void registerNetworkService (NetworkServices networkService, String networkServicePublicKey) throws CommunicationException;

    public void unregisterNetworkService (NetworkServices networkService) throws CommunicationException;

    public void requestConnectionTo(NetworkServices networkServices, String remoteNetworkService) throws CommunicationException;

    public void acceptIncomingNetworkServiceConnectionRequest (CommunicationChannels communicationChannel, NetworkServices networkService, String remoteNetworkService ) throws CommunicationException;

    public void rejectIncomingNetworkServiceConnectionRequest (CommunicationChannels communicationChannel, NetworkServices networkService, String remoteNetworkService, RejectConnectionRequestReasons reason ) throws CommunicationException;

    public Collection<String> getActiveNetworkServiceConnectionIdentifiers(NetworkServices networkService) throws CommunicationException;

    public ServiceToServiceOnlineConnection getActiveNetworkServiceConnection(CommunicationChannels communicationChannel, NetworkServices networkService, String remoteNetworkService) throws CommunicationException;

    public String getNetworkServiceChannelPublicKey(NetworkServices networkService) throws NetworkServiceNotRegisteredException;

// TODO: JORGE Manejo de Excepciones y entregarselas a Roberto
}
