package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by Nerio on 28/10/15.
 */
public class NewCryptoAddressRequestAssetUserActorNotificationEvent extends AbstractFermatEvent {

    private ActorAssetRedeemPoint actorAssetIssuerSender;
    private ActorAssetIssuer actorAssetUserDestination;
    private String message;

    public ActorAssetRedeemPoint getActorAssetIssuerSender() {
        return actorAssetIssuerSender;
    }

    public ActorAssetIssuer getActorAssetUserDestination() {
        return actorAssetUserDestination;
    }

    public String getMessage() {
        return message;
    }

    public void setNewCryptoAddressRequest(ActorAssetRedeemPoint actorRedeemPointSender, ActorAssetIssuer actorAssetIssuerDestination, String message) {
        this.actorAssetIssuerSender = actorRedeemPointSender;
        this.actorAssetUserDestination = actorAssetIssuerDestination;
    }

    public NewCryptoAddressRequestAssetUserActorNotificationEvent(EventType eventType) {
        super(eventType);
    }
}
