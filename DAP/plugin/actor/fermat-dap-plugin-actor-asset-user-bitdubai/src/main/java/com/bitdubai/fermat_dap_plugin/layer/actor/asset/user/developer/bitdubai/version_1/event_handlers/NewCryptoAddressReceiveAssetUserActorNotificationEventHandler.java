package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorNetworkServiceAssetUser;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.AssetActorUserPluginRoot;

/**
 * Created by Nerio on 28/10/15.
 */
public class NewCryptoAddressReceiveAssetUserActorNotificationEventHandler implements FermatEventHandler {


    private ActorNetworkServiceAssetUser actorNetworkServiceAssetUser;

    public NewCryptoAddressReceiveAssetUserActorNotificationEventHandler(ActorNetworkServiceAssetUser actorNetworkServiceAssetUser){
        this.actorNetworkServiceAssetUser = actorNetworkServiceAssetUser;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (((Service) this.actorNetworkServiceAssetUser).getStatus() == ServiceStatus.STARTED) {
//
//            NewCryptoAddressReceiveAssetUserActorNotificationEvent newCryptoAddressReceiveAssetUserActorNotificationEvent =      (NewCryptoAddressReceiveAssetUserActorNotificationEvent) platformEvent;
//
//            ActorAssetUser actorAssetUserSender = (ActorAssetUser) newCryptoAddressReceiveAssetUserActorNotificationEvent.getActorAssetUserSender();
//
//            ActorAssetIssuer actorAssetIssuerDestination = (ActorAssetIssuer) newCryptoAddressReceiveAssetUserActorNotificationEvent.getActorAssetIssuerDestination();
//
//            CryptoAddress cryptoAddress = (CryptoAddress) newCryptoAddressReceiveAssetUserActorNotificationEvent.getCryptoAddress();
//
//            this.actorNetworkServiceAssetUser.handleDeliveredCryptoAddresFromRemoteAssetUserEvent(actorAssetUserSender, cryptoAddress);
        }
    }
}
