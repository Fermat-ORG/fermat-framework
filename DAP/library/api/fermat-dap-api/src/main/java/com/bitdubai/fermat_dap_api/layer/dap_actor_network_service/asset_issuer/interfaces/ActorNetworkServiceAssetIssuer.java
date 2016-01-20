package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces;


import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;

import java.util.List;

/**
 * Created by rodrigo on 9/10/15.
 */
public interface ActorNetworkServiceAssetIssuer  {
    public void handleCompleteRequestListRegisteredAssetIssuerActorNetworksNotificationEvent(List<ActorAssetIssuer> actorAssetIssuerList);
    public void handleCompleteClientAssetIssuerActorRegistrationNotificationEvent(ActorAssetIssuer actorAssetIssuerList);
}
