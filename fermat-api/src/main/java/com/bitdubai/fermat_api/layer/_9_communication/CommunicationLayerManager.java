package com.bitdubai.fermat_api.layer._9_communication;

import com.bitdubai.fermat_api.layer._10_network_service.intra_user.IntraUser;

import java.util.UUID;

/**
 * Created by ciencias on 2/22/15.
 */
public interface CommunicationLayerManager {
    
    public void exposeUser (IntraUser intraUser);

    public UserToUserOnlineConnection connectTo (UUID intraUser) throws CantConnectToUserException;
}
