/*
* @#ActorNetworkServiceAssetUser.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces;

import java.util.List;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * The Class <code>com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.ActorNetworkServiceAssetUser</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 11/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface ActorNetworkServiceAssetUser {

    public void handleCompleteRequestListRegisteredAssetUserActorNetworksNotificationEvent(List<ActorAssetUser> actorAssetUserList);
    public void handleCompleteClientAssetUserActorRegistrationNotificationEvent(ActorAssetUser actorAssetUser);
    public void handleRequestCryptoAddresFromRemoteAssetUserEvent(ActorAssetUser actorAssetUser);
    public void handleDeliveredCryptoAddresFromRemoteAssetUserEvent(ActorAssetUser actorAssetUser, String cryptoAddress);

}
