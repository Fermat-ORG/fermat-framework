package com.bitdubai.fermat_core.layer._9_communication.cloud.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._10_network_service.NetworkService;
import com.bitdubai.fermat_api.layer._9_communication.OnlineChannel;
import com.bitdubai.fermat_api.layer._9_communication.ServiceToServiceOnlineConnection;

import java.util.UUID;

/**
 * Created by ciencias on 2/12/15.
 */
public class CloudOnlineChannel implements OnlineChannel {


    @Override
    public ServiceToServiceOnlineConnection createOnlineConnection(NetworkService localNetworkService, UUID remoteNetworkService) {
        return null;
    }
}
