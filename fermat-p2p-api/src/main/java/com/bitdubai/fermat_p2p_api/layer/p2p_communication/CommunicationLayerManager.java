package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud_server.enums.RejectConnectionRequestReasons;

import java.util.UUID;

/**
 * Created by ciencias on 2/22/15.
 */
public interface CommunicationLayerManager {
    
    public void registerNetworkService (NetworkServices networkService);

    public void unregisterNetworkService (NetworkServices networkService);
    
    public ServiceToServiceOnlineConnection acceptIncomingNetworkServiceConnectionRequest (CommunicationChannels communicationChannel, NetworkServices networkService, UUID localNetworkService, UUID remoteNetworkService ) throws  CommunicationChannelNotImplemented;

    public void rejectIncomingNetworkServiceConnectionRequest (CommunicationChannels communicationChannel, NetworkServices networkService, UUID localNetworkService, UUID remoteNetworkService, RejectConnectionRequestReasons reason ) throws  CommunicationChannelNotImplemented;

    public ServiceToServiceOnlineConnection connectTo (NetworkServices networkServices, UUID remoteNetworkService) throws CantConnectToRemoteServiceException;
}
