package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorNetworkServiceAssetUser;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.NewCryptoAddressRequestAssetUserActorNotificationEvent;

/**
 * Created by Nerio on 28/10/15.
 */
public class NewCryptoAddressRequestAssetUserActorNotificationEventHandler implements FermatEventHandler {

    private ActorNetworkServiceAssetUser actorNetworkServiceAssetUser;

    public NewCryptoAddressRequestAssetUserActorNotificationEventHandler(ActorNetworkServiceAssetUser actorNetworkServiceAssetUser) {
        this.actorNetworkServiceAssetUser = actorNetworkServiceAssetUser;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {

        if (((Service) this.actorNetworkServiceAssetUser).getStatus() == ServiceStatus.STARTED) {

            System.out.println("Actor Asset User Request Crypto Address - handleEvent =" + fermatEvent);

            NewCryptoAddressRequestAssetUserActorNotificationEvent newCryptoAddressRequestAssetUserActorNotificationEvent = (NewCryptoAddressRequestAssetUserActorNotificationEvent) fermatEvent;

            ActorAssetIssuer actorAssetIssuerSender = (ActorAssetIssuer) newCryptoAddressRequestAssetUserActorNotificationEvent.getActorAssetIssuerSender();

            ActorAssetUser actorAssetUserDestination = (ActorAssetUser) newCryptoAddressRequestAssetUserActorNotificationEvent.getActorAssetUserDestination();

            System.out.println("Actor Asset User: llamando a handleRequestCrypto en Actor AssetUserActorPluginRoot");

            this.actorNetworkServiceAssetUser.handleRequestCryptoAddresFromRemoteAssetUserEvent(actorAssetIssuerSender, actorAssetUserDestination);
        }
    }
}
