package com.bitdubai.fermat_dap_api.layer.all_definition.events;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventType;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;

/**
 * Created by Nerio on 25/11/15.
 */
public class NewReceiveMessageActorNotificationEvent extends AbstractDAPEvent {

    private ActorAssetUser actorAssetUserSender;
    private ActorAssetIssuer actorAssetIssuerDestination;
    private CryptoAddress cryptoAddress;

    public NewReceiveMessageActorNotificationEvent(EventType eventType) {
        super(eventType);
    }

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
}
