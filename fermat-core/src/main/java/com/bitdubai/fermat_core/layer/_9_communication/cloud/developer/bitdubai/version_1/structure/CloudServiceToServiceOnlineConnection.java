package com.bitdubai.fermat_core.layer._9_communication.cloud.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer._4_user.device_user.DeviceUser;
import com.bitdubai.fermat_api.layer._9_communication.CantConnectToRemoteServiceException;
import com.bitdubai.fermat_api.layer._9_communication.ConnectionStatus;
import com.bitdubai.fermat_api.layer._9_communication.ServiceToServiceOnlineConnection;

import java.util.UUID;

/**
 * Created by ciencias on 2/12/15.
 */
public class CloudServiceToServiceOnlineConnection implements ServiceToServiceOnlineConnection {

    NetworkServices networkService;
    UUID remoteNetworkService;
    
    public CloudServiceToServiceOnlineConnection(NetworkServices networkService, UUID remoteNetworkService) {

        this.networkService = networkService;
        this.remoteNetworkService = remoteNetworkService;
        
    }
    


    @Override
    public void reConnect() throws CantConnectToRemoteServiceException {
        
    }

    @Override
    public void disconnect() {

    }

    @Override
    public ConnectionStatus getStatus() {
        return null;
    }
}
