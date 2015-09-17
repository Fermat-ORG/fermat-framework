package com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantAcceptIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IntraUserActorConnectionAcceptedEvent;

/**
 * Created by natalia on 14/08/15.
 */
public class IntraUserConnectionAcceptedEventHandlers implements EventHandler {
    /**
     * listener  INTRA_USER_CONNECTION_ACCEPTED event
     * Change Actor status to ACCEPTED
     */
    ActorIntraUserManager actorIntraUserManager;
    EventManager eventManager;
    EventMonitor eventMonitor;

    IntraUserManager intraUserNetworkServiceManager;

    public void setActorIntraUserManager(ActorIntraUserManager actorIntraUserManager){
        this.actorIntraUserManager = actorIntraUserManager;

    }

    public void setEventManager(EventManager eventManager){
        this.eventManager = eventManager;

    }

    public void setEventManager(EventMonitor eventMonitor){
        this.eventMonitor = eventMonitor;

    }

    public void setIntraUserManager( IntraUserManager intraUserNetworkServiceManager){
        this.intraUserNetworkServiceManager = intraUserNetworkServiceManager;

    }

    @Override
    public void handleEvent(PlatformEvent platformEvent) throws FermatException {
        if (((Service) this.actorIntraUserManager).getStatus() == ServiceStatus.STARTED){

            try
            {
                IntraUserActorConnectionAcceptedEvent intraUserActorConnectionAcceptedEvent = (IntraUserActorConnectionAcceptedEvent) platformEvent;
                /**
                 * Change Intra User Actor Status To Connected
                 */
                this.actorIntraUserManager.acceptIntraUser(intraUserActorConnectionAcceptedEvent.getIntraUserLoggedInPublicKey(),
                        intraUserActorConnectionAcceptedEvent.getIntraUserToAddPublicKey());
                /**
                 * Confirm connexion on Network services
                 */
                intraUserNetworkServiceManager.confirmNotification(intraUserActorConnectionAcceptedEvent.getIntraUserLoggedInPublicKey(), intraUserActorConnectionAcceptedEvent.getIntraUserToAddPublicKey());
                /**
                 * fire event "INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION"
                 */
                PlatformEvent event =  eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION);
                eventManager.raiseEvent(event);

            }
            catch(CantAcceptIntraUserException e)
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
