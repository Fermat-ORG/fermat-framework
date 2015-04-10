package com.bitdubai.fermat_api.layer._9_communication;

import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer._9_communication.cloud.RejectConnectionRequestReasons;

import java.util.UUID;

/**
 * Created by ciencias on 31.12.14.
 */
public interface CommunicationChannel {
    
    public OnlineChannel createOnlineChannel ();

    public void unregisterNetworkService (UUID networkService);

    public void registerNetworkService (NetworkServices networkServices,UUID networkService);

    public ServiceToServiceOnlineConnection acceptIncomingNetworkServiceConnectionRequest (NetworkServices networkService, UUID localNetworkService, UUID remoteNetworkService );

    public void rejectIncomingNetworkServiceConnectionRequest (NetworkServices networkService, UUID localNetworkService, UUID remoteNetworkService, RejectConnectionRequestReasons reason );



}
