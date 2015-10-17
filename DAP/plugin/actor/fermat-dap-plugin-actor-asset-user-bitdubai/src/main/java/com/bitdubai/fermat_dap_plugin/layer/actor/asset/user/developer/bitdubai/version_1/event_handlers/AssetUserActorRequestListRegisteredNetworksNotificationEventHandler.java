package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.ActorNetworkServiceAssetUser;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.AssetActorUserPluginRoot;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.AssetUserActorRequestListRegisteredNetworkServiceNotificationEvent;

/**
 * Created by Nerio on 08/10/15.
 */
public class AssetUserActorRequestListRegisteredNetworksNotificationEventHandler implements FermatEventHandler {

    private AssetActorUserPluginRoot assetActorUserPluginRoot;

    public AssetUserActorRequestListRegisteredNetworksNotificationEventHandler(AssetActorUserPluginRoot assetActorUserPluginRoot) {
        this.assetActorUserPluginRoot = assetActorUserPluginRoot;
    }

    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {

        System.out.println("Succesfull Request AssetuserActor List  - handleEvent =" + platformEvent);

        if (((Service) this.assetActorUserPluginRoot).getStatus() == ServiceStatus.STARTED) {

            AssetUserActorRequestListRegisteredNetworkServiceNotificationEvent completeRequestListRegisteredAssetUserActorNetworksNotificationEvent = (AssetUserActorRequestListRegisteredNetworkServiceNotificationEvent) platformEvent;
             /*
             *  ActorNetworkServiceAssetUser make the job
             */
            this.assetActorUserPluginRoot.handleCompleteRequestListRegisteredAssetUserActorNetworksNotificationEvent(completeRequestListRegisteredAssetUserActorNetworksNotificationEvent.getActorAssetUserList());
        }
    }
}
