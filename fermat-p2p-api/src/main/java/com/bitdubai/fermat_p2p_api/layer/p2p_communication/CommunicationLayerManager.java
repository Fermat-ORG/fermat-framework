package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud_server.enums.RejectConnectionRequestReasons;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by ciencias on 2/22/15.
 */
public interface CommunicationLayerManager {
    
    public void registerNetworkService (NetworkServices networkService);

    public void unregisterNetworkService (NetworkServices networkService);

    public void requestConnectionTo(NetworkServices networkServices, String remoteNetworkService) throws CantConnectToRemoteServiceException;

    public void acceptIncomingNetworkServiceConnectionRequest (CommunicationChannels communicationChannel, NetworkServices networkService, String remoteNetworkService ) throws  CommunicationChannelNotImplemented;

    public void rejectIncomingNetworkServiceConnectionRequest (CommunicationChannels communicationChannel, NetworkServices networkService, String remoteNetworkService, RejectConnectionRequestReasons reason ) throws  CommunicationChannelNotImplemented;

    public Collection<String> getActiveNetworkServiceConnectionIdentifiers(NetworkServices networkService);

    public ServiceToServiceOnlineConnection getActiveNetworkServiceConnection(CommunicationChannels communicationChannel, NetworkServices networkService, String remoteNetworkService);

// TODO: JORGE Manejo de Excepciones y entregarselas a Roberto
}
