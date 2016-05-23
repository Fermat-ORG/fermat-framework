package org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;

import org.fermat.fermat_dap_api.layer.all_definition.events.ActorAssetUserCompleteRegistrationNotificationEvent;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorNetworkServiceAssetUser;

/**
 * Created by Nerio on 08/10/15.
 */
public class AssetUserActorCompleteRegistrationNotificationEventHandler implements FermatEventHandler {

    private ActorNetworkServiceAssetUser actorNetworkServiceAssetUser;

    public AssetUserActorCompleteRegistrationNotificationEventHandler(ActorNetworkServiceAssetUser actorNetworkServiceAssetUser) {
        this.actorNetworkServiceAssetUser = actorNetworkServiceAssetUser;
    }

    @Override
    public void handleEvent(FermatEvent platformEvent) throws FermatException {

        System.out.println("Actor Asset User Succesfull register AssetuserActor - handleEvent =" + platformEvent);

        if (((Service) this.actorNetworkServiceAssetUser).getStatus() == ServiceStatus.STARTED) {

            ActorAssetUserCompleteRegistrationNotificationEvent completeClientAssetUserActorRegistrationNotificationEvent = (ActorAssetUserCompleteRegistrationNotificationEvent) platformEvent;
             /*
             *  ActorNetworkServiceAssetUser make the job
             */
            this.actorNetworkServiceAssetUser.handleCompleteClientAssetUserActorRegistrationNotificationEvent(completeClientAssetUserActorRegistrationNotificationEvent.getActorAssetUser());
        }
    }
}
