package com.bitdubai.fermat_api.layer._9_communication;

import com.bitdubai.fermat_api.layer._10_network_service.NetworkService;
import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer._9_communication.cloud.RejectConnectionRequestReasons;

import java.util.UUID;

/**
 * Created by ciencias on 2/22/15.
 */
public interface CommunicationLayerManager {
    
    public void registerNetworkService (NetworkServices networkServices,UUID networkService);

    public void unregisterNetworkService (UUID networkService);
    
    public ServiceToServiceOnlineConnection acceptIncomingNetworkServiceConnectionRequest (CommunicationChannels communicationChannel, NetworkServices networkService, UUID localNetworkService, UUID remoteNetworkService ) throws  CommunicationChannelNotImplemented;

    public ServiceToServiceOnlineConnection rejectIncomingNetworkServiceConnectionRequest (CommunicationChannels communicationChannel, NetworkServices networkService, UUID localNetworkService, UUID remoteNetworkService, RejectConnectionRequestReasons reason ) throws  CommunicationChannelNotImplemented;

    public ServiceToServiceOnlineConnection connectTo (NetworkServices networkServices, UUID networkServiceId) throws CantConnectToRemoteServiceException;
}
