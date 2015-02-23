package com.bitdubai.fermat_core.layer._9_communication.cloud.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;
import com.bitdubai.fermat_api.layer._4_user.device_user.DeviceUser;
import com.bitdubai.fermat_api.layer._9_communication.*;

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

    @Override
    public void sendMessage(Message message) throws CantSendMessageException {

    }

    @Override
    public int getUnreadMessagesCount() {
        return 0;
    }

    @Override
    public Message readNextMessage() {
        return null;
    }

}
