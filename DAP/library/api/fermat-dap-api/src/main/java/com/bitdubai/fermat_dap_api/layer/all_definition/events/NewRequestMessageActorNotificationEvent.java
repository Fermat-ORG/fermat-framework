package com.bitdubai.fermat_dap_api.layer.all_definition.events;

import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;

/**
 * Created by Nerio on 25/11/15.
 */
public class NewRequestMessageActorNotificationEvent extends AbstractDAPEvent {

    private ActorAssetRedeemPoint actorAssetIssuerSender;
    private ActorAssetIssuer actorAssetUserDestination;
    private String message;

    public NewRequestMessageActorNotificationEvent(EventType eventType) {
        super(eventType);
    }

    public ActorAssetRedeemPoint getActorAssetIssuerSender() {
        return actorAssetIssuerSender;
    }

    public ActorAssetIssuer getActorAssetUserDestination() {
        return actorAssetUserDestination;
    }

    public String getMessage() {
        return message;
    }

    public void setNewRequestMessage(ActorAssetRedeemPoint actorRedeemPointSender, ActorAssetIssuer actorAssetIssuerDestination, String message) {
        this.actorAssetIssuerSender = actorRedeemPointSender;
        this.actorAssetUserDestination = actorAssetIssuerDestination;
        this.message = message;
    }

}
