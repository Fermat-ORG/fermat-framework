package com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces;

import java.util.List;

/**
 * Created by Nerio on 19/10/15.
 */
public interface ActorNetworkServiceAssetUser {

    void handleCompleteRequestListRegisteredAssetUserActorNetworksNotificationEvent(List<ActorAssetUser> actorAssetUserList);

    void handleCompleteClientAssetUserActorRegistrationNotificationEvent(ActorAssetUser actorAssetUser);

    void handleCompleteRequestCryptoAddressNotificationEvent(ActorAssetUser actorAssetUser);

    void handleCompleteSendCryptoAddressNotificationEvent(ActorAssetUser actorAssetUser);

}
