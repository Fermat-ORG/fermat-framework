package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;

import java.util.List;

/**
 * Created by rodrigo on 9/10/15.
 */
public interface ActorNetworkServiceRedeemPoint {
    public void handleCompleteRequestListRegisteredAssetRedeemPointActorNetworksNotificationEvent(List<ActorAssetRedeemPoint> actorAssetRedeemPointList);
    public void handleCompleteClientAssetRedeemPointActorRegistrationNotificationEvent(ActorAssetRedeemPoint actorAssetRedeemPoint);

}
