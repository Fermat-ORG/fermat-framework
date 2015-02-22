package com.bitdubai.fermat_api.layer._9_communication;

import com.bitdubai.fermat_api.layer._10_network_service.NetworkService;
import com.bitdubai.fermat_api.layer._10_network_service.intra_user.IntraUser;

import java.util.UUID;

/**
 * Created by ciencias on 2/12/15.
 */
public interface OnlineChannel {

    public ServiceToServiceOnlineConnection createOnlineConnection (NetworkService localNetworkService, UUID remoteNetworkService);
    
}
