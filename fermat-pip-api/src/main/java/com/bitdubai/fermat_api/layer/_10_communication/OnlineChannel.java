package com.bitdubai.fermat_api.layer._10_communication;

import com.bitdubai.fermat_api.layer._1_definition.enums.NetworkServices;

import java.util.UUID;

/**
 * Created by ciencias on 2/12/15.
 */
public interface OnlineChannel {

    public ServiceToServiceOnlineConnection connectTo (NetworkServices networkServices, UUID remoteNetworkService) throws CantConnectToRemoteServiceException;

    
}
