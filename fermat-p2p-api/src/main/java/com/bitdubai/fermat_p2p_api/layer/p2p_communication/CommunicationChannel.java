package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.cloud_server.enums.RejectConnectionRequestReasons;

import java.util.Collection;

/**
 * Created by ciencias on 31.12.14.
 */
public interface CommunicationChannel {
    
    public String getChannelPublicKey();
    
    public void registerNetworkService (NetworkServices networkService);
    
    public void unregisterNetworkService (NetworkServices networkService);
    
    public String getNetworkServiceChannelPublicKey(NetworkServices networkService);
    
    public Collection<String> getIncomingNetworkServiceConnectionRequests(NetworkServices networkService);
    
    public void acceptIncomingNetworkServiceConnectionRequest (NetworkServices networkService, String remoteNetworkService);
    
    public ServiceToServiceOnlineConnection getActiveNetworkServiceConnection(NetworkServices networkService, String remoteNetworkService);

    public void rejectIncomingNetworkServiceConnectionRequest (NetworkServices networkService, String remoteNetworkService, RejectConnectionRequestReasons reason );

}
