package org.fermat.fermat_dap_api.layer.all_definition.events;

import org.fermat.fermat_dap_api.layer.all_definition.enums.EventType;

/**
 * Created by Nerio on 02/11/15.
 */
public class ActorAssetRedeemPointCompleteRegistrationNotificationEvent extends AbstractDAPEvent {

    private org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint actorAssetRedeemPoint;

    public org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint getActorAssetRedeemPoint() {
        return actorAssetRedeemPoint;
    }

    public void setActorAssetRedeemPoint(org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint actorAssetRedeemPoint) {
        this.actorAssetRedeemPoint = actorAssetRedeemPoint;
    }

    public ActorAssetRedeemPointCompleteRegistrationNotificationEvent(EventType eventType) {
        super(eventType);
    }
}
