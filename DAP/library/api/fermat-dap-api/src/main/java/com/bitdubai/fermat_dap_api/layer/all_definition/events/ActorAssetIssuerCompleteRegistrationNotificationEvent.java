package com.bitdubai.fermat_dap_api.layer.all_definition.events;

import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;

/**
 * Created by Nerio on 02/11/15.
 */
public class ActorAssetIssuerCompleteRegistrationNotificationEvent extends AbstractDAPEvent {

    private ActorAssetIssuer actorAssetIssuer;

    public ActorAssetIssuer getActorAssetIssuer() {
        return actorAssetIssuer;
    }

    public void setActorAssetIssuer(ActorAssetIssuer actorAssetIssuer) {
        this.actorAssetIssuer = actorAssetIssuer;
    }

    public ActorAssetIssuerCompleteRegistrationNotificationEvent(EventType eventType) {
        super(eventType);
    }
}
