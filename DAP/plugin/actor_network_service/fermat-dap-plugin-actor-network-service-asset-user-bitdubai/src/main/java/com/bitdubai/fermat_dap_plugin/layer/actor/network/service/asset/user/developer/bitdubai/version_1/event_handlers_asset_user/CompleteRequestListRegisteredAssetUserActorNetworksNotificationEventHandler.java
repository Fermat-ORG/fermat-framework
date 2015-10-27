/*
* @#CompleteRequestListRegisteredAssetUserActorNetworksNotificationEventHandler.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.event_handlers_asset_user;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.ActorNetworkServiceAssetUser;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.AssetUserActorRequestListRegisteredNetworkServiceNotificationEvent;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_1.event_handlers_asset_user.CompleteRequestListRegisteredAssetUserActorNetworksNotificationEventHandler</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 12/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CompleteRequestListRegisteredAssetUserActorNetworksNotificationEventHandler implements FermatEventHandler {


    private ActorNetworkServiceAssetUser actorNetworkServiceAssetUser;

    public CompleteRequestListRegisteredAssetUserActorNetworksNotificationEventHandler(ActorNetworkServiceAssetUser actorNetworkServiceAssetUser){

        this.actorNetworkServiceAssetUser=actorNetworkServiceAssetUser;
    }


    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {

        System.out.println("Succesfull Request AssetuserActor List  - handleEvent ="+platformEvent );

        if (((Service) this.actorNetworkServiceAssetUser).getStatus() == ServiceStatus.STARTED) {

            AssetUserActorRequestListRegisteredNetworkServiceNotificationEvent completeRequestListRegisteredAssetUserActorNetworksNotificationEvent = (AssetUserActorRequestListRegisteredNetworkServiceNotificationEvent) platformEvent;

             /*
             *  ActorNetworkServiceAssetUser make the job
             */

           // this.actorNetworkServiceAssetUser.handleCompleteRequestListRegisteredAssetUserActorNetworksNotificationEvent(completeRequestListRegisteredAssetUserActorNetworksNotificationEvent.getActorAssetUserList());



        }

    }
}