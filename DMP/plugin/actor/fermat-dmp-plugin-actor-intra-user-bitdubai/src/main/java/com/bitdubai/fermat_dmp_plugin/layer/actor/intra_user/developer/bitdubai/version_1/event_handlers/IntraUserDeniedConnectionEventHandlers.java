package com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IntraUserActorConnectionDeniedEvent;

/**
 * Created by natalia on 17/08/15.
 */
public class IntraUserDeniedConnectionEventHandlers implements EventHandler {
    /**
     * listener  INTRA_USER_CONNECTION_DENIED event
     * Change Actor status to DENIED
     */
    ActorIntraUserManager actorIntraUserManager;

    public void setActorIntraUserManager(ActorIntraUserManager actorIntraUserManager){
        this.actorIntraUserManager = actorIntraUserManager;

    }

    @Override
    public void handleEvent(PlatformEvent platformEvent) throws FermatException {
        if (((Service) this.actorIntraUserManager).getStatus() == ServiceStatus.STARTED){

           IntraUserActorConnectionDeniedEvent intraUserActorConnectionDeniedEvent = (IntraUserActorConnectionDeniedEvent) platformEvent;
            this.actorIntraUserManager.denyConnection(intraUserActorConnectionDeniedEvent.getIntraUserLoggedInPublicKey(),
                    intraUserActorConnectionDeniedEvent.getIntraUserToAddPublicKey());



        }
        else
        {
            throw new TransactionServiceNotStartedException();
        }
    }
}
