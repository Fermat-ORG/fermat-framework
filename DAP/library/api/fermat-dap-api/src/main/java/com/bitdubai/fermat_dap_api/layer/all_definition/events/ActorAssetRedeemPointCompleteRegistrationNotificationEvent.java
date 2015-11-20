package com.bitdubai.fermat_dap_api.layer.all_definition.events;

import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;

/**
 * Created by Nerio on 02/11/15.
 */
public class ActorAssetRedeemPointCompleteRegistrationNotificationEvent extends AbstractDAPEvent {

    private ActorAssetRedeemPoint actorAssetRedeemPoint;

    public ActorAssetRedeemPoint getActorAssetRedeemPoint() {
        return actorAssetRedeemPoint;
    }

    public void setActorAssetRedeemPoint(ActorAssetRedeemPoint actorAssetRedeemPoint) {
        this.actorAssetRedeemPoint = actorAssetRedeemPoint;
    }

    public ActorAssetRedeemPointCompleteRegistrationNotificationEvent(EventType eventType) {
        super(eventType);
    }
}
