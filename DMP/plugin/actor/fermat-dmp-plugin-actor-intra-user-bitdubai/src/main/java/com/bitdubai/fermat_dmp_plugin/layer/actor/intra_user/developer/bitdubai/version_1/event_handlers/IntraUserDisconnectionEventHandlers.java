package com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantDisconnectIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IntraUserActorConnectionCancelledEvent;

/**
 * Created by natalia on 14/08/15.
 */
public class IntraUserDisconnectionEventHandlers implements EventHandler {
    /**
     * listener  INTRA_USER_CONNECTION_CANCELLED event
     * Change Actor status to CANCELLED
     */
    ActorIntraUserManager actorIntraUserManager;
    IntraUserManager intraUserNetworkServiceManager;

    EventMonitor eventMonitor;

    public void setEventManager(EventMonitor eventMonitor){
        this.eventMonitor = eventMonitor;

    }

    public void setActorIntraUserManager(ActorIntraUserManager actorIntraUserManager){
        this.actorIntraUserManager = actorIntraUserManager;

    }

    public void setIntraUserManager( IntraUserManager intraUserNetworkServiceManager){
        this.intraUserNetworkServiceManager = intraUserNetworkServiceManager;

    }

    @Override
    public void handleEvent(PlatformEvent platformEvent) throws FermatException {
        if (((Service) this.actorIntraUserManager).getStatus() == ServiceStatus.STARTED){
            try
            {
                IntraUserActorConnectionCancelledEvent intraUserActorConnectionCancelledEvent = (IntraUserActorConnectionCancelledEvent) platformEvent;
                this.actorIntraUserManager.disconnectIntraUser(intraUserActorConnectionCancelledEvent.getIntraUserLoggedInPublicKey(),
                        intraUserActorConnectionCancelledEvent.getIntraUserToAddPublicKey());

                /**
                 * Confirm Disconnect on Network services
                 */

                intraUserNetworkServiceManager.confirmNotification(intraUserActorConnectionCancelledEvent.getIntraUserLoggedInPublicKey(), intraUserActorConnectionCancelledEvent.getIntraUserToAddPublicKey());
            }
            catch(CantDisconnectIntraUserException e)
            {
                this.eventMonitor.handleEventException(e,platformEvent);
            }
            catch(Exception e)
            {
                this.eventMonitor.handleEventException(e,platformEvent);
            }

        }
        else
        {
            throw new TransactionServiceNotStartedException();
        }
    }
}
