package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by Nerio on 28/10/15.
 */
public class NewCryptoAddressRequestAssetUserActorNotificationEvent extends AbstractFermatEvent{

    private ActorAssetIssuer actorAssetIssuerSender;
    private ActorAssetUser actorAssetUserDestination;

    public ActorAssetIssuer getActorAssetIssuerSender() {
        return actorAssetIssuerSender;
    }

    public ActorAssetUser getActorAssetUserDestination() {
        return actorAssetUserDestination;
    }

    public void setNewCryptoAddressRequest(ActorAssetIssuer actorAssetIssuerSender, ActorAssetUser actorAssetUserDestination){
        this.actorAssetIssuerSender=actorAssetIssuerSender;
        this.actorAssetUserDestination=actorAssetUserDestination;
    }

    public NewCryptoAddressRequestAssetUserActorNotificationEvent(EventType eventType) {
        super(eventType);
    }
}
