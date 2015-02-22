package com.bitdubai.fermat_api.layer._9_communication;

import com.bitdubai.fermat_api.layer._10_network_service.NetworkService;
import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;

import java.util.UUID;

/**
 * Created by ciencias on 2/22/15.
 */
public interface CommunicationLayerManager {
    
    public void registerNetworkService (NetworkService networkService, NetworkServices networkServices);

    public void unregisterNetworkService (NetworkServices networkServices);

    public ServiceToServiceOnlineConnection connectTo (NetworkServices networkServices, UUID networkServiceId) throws CantConnectToRemoteServiceException;
}
