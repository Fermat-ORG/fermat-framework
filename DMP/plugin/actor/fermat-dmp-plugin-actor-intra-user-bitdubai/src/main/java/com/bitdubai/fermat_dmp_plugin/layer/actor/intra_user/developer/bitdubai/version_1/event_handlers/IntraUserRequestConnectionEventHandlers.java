package com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IntraUserActorRequestConnectionEvent;

/**
 * Created by natalia on 17/08/15.
 */
public class IntraUserRequestConnectionEventHandlers implements EventHandler {
    /**
     * listener  INTRA_USER_REQUESTED_CONNECTION event
     * Change Actor status to PENDING_LOCALLY_ACCEPTANCE
     */
    ActorIntraUserManager actorIntraUserManager;

    public void setActorIntraUserManager(ActorIntraUserManager actorIntraUserManager){
        this.actorIntraUserManager = actorIntraUserManager;

    }

    @Override
    public void handleEvent(PlatformEvent platformEvent) throws FermatException {
        if (((Service) this.actorIntraUserManager).getStatus() == ServiceStatus.STARTED){

            IntraUserActorRequestConnectionEvent intraUserActorRequestConnectionEvent = (IntraUserActorRequestConnectionEvent) platformEvent;
            this.actorIntraUserManager.receivingIntraUserRequestConnection(intraUserActorRequestConnectionEvent.getIntraUserLoggedInPublicKey(),
                    intraUserActorRequestConnectionEvent.getIntraUserToAddName(),
                    intraUserActorRequestConnectionEvent.getIntraUserToAddPublicKey(),
                    intraUserActorRequestConnectionEvent.getIntraUserProfileImage());



        }
        else
        {
            throw new TransactionServiceNotStartedException();
        }
    }

    }

