package com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;

/**
 * Created by Nerio on 28/10/15.
 */
public class NewCryptoAddressReceiveAssetUserActorNotificationEvent extends AbstractFermatEvent{

    private ActorAssetUser actorAssetUserSender;
    private ActorAssetIssuer actorAssetIssuerDestination;
    private CryptoAddress cryptoAddress;

    public ActorAssetUser getActorAssetUserSender() {
        return actorAssetUserSender;
    }

    public ActorAssetIssuer getActorAssetIssuerDestination() {
        return actorAssetIssuerDestination;
    }

    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }

    public void setNewCryptoAddressReceive(ActorAssetUser actorAssetUserSender, ActorAssetIssuer actorAssetIssuerDestination, CryptoAddress cryptoAddress){
        this.actorAssetUserSender=actorAssetUserSender;
        this.actorAssetIssuerDestination=actorAssetIssuerDestination;
        this.cryptoAddress=cryptoAddress;
    }

    public NewCryptoAddressReceiveAssetUserActorNotificationEvent(EventType eventType) {
        super(eventType);
    }
}
