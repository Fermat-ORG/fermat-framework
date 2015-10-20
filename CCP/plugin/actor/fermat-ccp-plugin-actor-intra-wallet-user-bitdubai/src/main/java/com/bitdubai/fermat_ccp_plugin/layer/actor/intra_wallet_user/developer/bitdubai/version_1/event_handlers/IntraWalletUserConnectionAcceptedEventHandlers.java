package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantAcceptIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.IntraWalletUserManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IntraUserActorConnectionAcceptedEvent;

/**
 * Created by natalia on 14/08/15.
 */
public class IntraWalletUserConnectionAcceptedEventHandlers implements FermatEventHandler {
    /**
     * listener  INTRA_USER_CONNECTION_ACCEPTED event
     * Change Actor status to ACCEPTED
     */
    IntraWalletUserManager actorIntraUserManager;
    EventManager eventManager;
    FermatEventMonitor fermatEventMonitor;

    IntraUserManager intraUserNetworkServiceManager;

    public void setIntraWalletUserManager(IntraWalletUserManager actorIntraUserManager){
        this.actorIntraUserManager = actorIntraUserManager;

    }

    public void setEventManager(EventManager eventManager){
        this.eventManager = eventManager;

    }

    public void setEventManager(FermatEventMonitor fermatEventMonitor){
        this.fermatEventMonitor = fermatEventMonitor;

    }

    public void setIntraUserManager( IntraUserManager intraUserNetworkServiceManager){
        this.intraUserNetworkServiceManager = intraUserNetworkServiceManager;

    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (((Service) this.actorIntraUserManager).getStatus() == ServiceStatus.STARTED){

            try
            {
                IntraUserActorConnectionAcceptedEvent intraUserActorConnectionAcceptedEvent = (IntraUserActorConnectionAcceptedEvent) fermatEvent;
                /**
                 * Change Intra User Actor Status To Connected
                 */
                this.actorIntraUserManager.acceptIntraWalletUser(intraUserActorConnectionAcceptedEvent.getIntraUserLoggedInPublicKey(),
                        intraUserActorConnectionAcceptedEvent.getIntraUserToAddPublicKey());
                /**
                 * Confirm connexion on Network services
                 */
                intraUserNetworkServiceManager.confirmNotification(intraUserActorConnectionAcceptedEvent.getIntraUserLoggedInPublicKey(), intraUserActorConnectionAcceptedEvent.getIntraUserToAddPublicKey());
                /**
                 * fire event "INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION"
                 */
                FermatEvent event =  eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION);
                eventManager.raiseEvent(event);

            }
            catch(CantAcceptIntraWalletUserException e)
            {
                this.fermatEventMonitor.handleEventException(e, fermatEvent);
            }

            catch(Exception e)
            {
                this.fermatEventMonitor.handleEventException(e, fermatEvent);
            }

        }
        else
        {
            throw new TransactionServiceNotStartedException();
        }



    }
}
