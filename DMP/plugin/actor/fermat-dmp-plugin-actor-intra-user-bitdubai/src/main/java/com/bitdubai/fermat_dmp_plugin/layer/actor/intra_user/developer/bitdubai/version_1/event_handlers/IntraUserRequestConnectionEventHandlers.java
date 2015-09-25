package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.ccp_actor.intra_user.exceptions.CantCreateIntraUserException;
import com.bitdubai.fermat_api.layer.ccp_actor.intra_user.interfaces.ActorIntraUserManager;
import com.bitdubai.fermat_api.layer.ccp_network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.ccp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IntraUserActorRequestConnectionEvent;

/**
 * Created by natalia on 17/08/15.
 */
public class IntraUserRequestConnectionEventHandlers implements FermatEventHandler {
    /**
     * listener  INTRA_USER_REQUESTED_CONNECTION event
     * Change Actor status to PENDING_LOCALLY_ACCEPTANCE
     */
    ActorIntraUserManager actorIntraUserManager;
    EventManager eventManager;
    IntraUserManager intraUserNetworkServiceManager;


    FermatEventMonitor fermatEventMonitor;

    public void setEventManager(FermatEventMonitor fermatEventMonitor){
        this.fermatEventMonitor = fermatEventMonitor;

    }

    public void setActorIntraUserManager(ActorIntraUserManager actorIntraUserManager){
        this.actorIntraUserManager = actorIntraUserManager;

    }

    public void setEventManager(EventManager eventManager){
        this.eventManager = eventManager;

    }

    public void setIntraUserManager( IntraUserManager intraUserNetworkServiceManager){
        this.intraUserNetworkServiceManager = intraUserNetworkServiceManager;

    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (((Service) this.actorIntraUserManager).getStatus() == ServiceStatus.STARTED){

            try
            {
                IntraUserActorRequestConnectionEvent intraUserActorRequestConnectionEvent = (IntraUserActorRequestConnectionEvent) fermatEvent;
                this.actorIntraUserManager.receivingIntraUserRequestConnection(intraUserActorRequestConnectionEvent.getIntraUserLoggedInPublicKey(),
                        intraUserActorRequestConnectionEvent.getIntraUserToAddName(),
                        intraUserActorRequestConnectionEvent.getIntraUserToAddPublicKey(),
                        intraUserActorRequestConnectionEvent.getIntraUserProfileImage());

                /**
                 * Confirm connection on Network services
                 */

                intraUserNetworkServiceManager.confirmNotification(intraUserActorRequestConnectionEvent.getIntraUserLoggedInPublicKey(), intraUserActorRequestConnectionEvent.getIntraUserToAddPublicKey());


                /**
                 * fire event "INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION"
                 */
                FermatEvent event =  eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION);
                eventManager.raiseEvent(event);

            }
            catch(CantCreateIntraUserException e)
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

