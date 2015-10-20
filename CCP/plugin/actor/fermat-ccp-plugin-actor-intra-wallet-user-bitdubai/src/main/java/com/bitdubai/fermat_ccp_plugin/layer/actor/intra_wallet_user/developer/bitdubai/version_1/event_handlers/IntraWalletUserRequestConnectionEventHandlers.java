package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantCreateIntraWalletUserException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.IntraWalletUserActorPluginRoot;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IntraUserActorRequestConnectionEvent;

/**
 * Created by natalia on 17/08/15.
 */
public class IntraWalletUserRequestConnectionEventHandlers implements FermatEventHandler {
    /**
     * listener  INTRA_USER_REQUESTED_CONNECTION event
     * Change Actor status to PENDING_LOCALLY_ACCEPTANCE
     */
    IntraWalletUserActorPluginRoot actorIntraUserManager;
    EventManager eventManager;
    IntraUserManager intraUserNetworkServiceManager;


    FermatEventMonitor fermatEventMonitor;

    public void setEventManager(FermatEventMonitor fermatEventMonitor){
        this.fermatEventMonitor = fermatEventMonitor;

    }

    public void setIntraWalletUserManager(IntraWalletUserActorPluginRoot actorIntraUserManager){
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
                this.actorIntraUserManager.receivingIntraWalletUserRequestConnection(intraUserActorRequestConnectionEvent.getIntraUserLoggedInPublicKey(),
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
            catch(CantCreateIntraWalletUserException e)
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

