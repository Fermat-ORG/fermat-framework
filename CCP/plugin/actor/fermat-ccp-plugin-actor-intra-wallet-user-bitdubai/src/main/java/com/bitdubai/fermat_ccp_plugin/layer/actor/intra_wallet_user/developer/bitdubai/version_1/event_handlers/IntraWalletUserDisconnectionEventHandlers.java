package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantDisconnectIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.IntraWalletUserManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IntraUserActorConnectionCancelledEvent;

/**
 * Created by natalia on 14/08/15.
 */
public class IntraWalletUserDisconnectionEventHandlers implements FermatEventHandler {
    /**
     * listener  INTRA_USER_CONNECTION_CANCELLED event
     * Change Actor status to CANCELLED
     */
    IntraWalletUserManager actorIntraUserManager;
    IntraUserManager intraUserNetworkServiceManager;

    FermatEventMonitor fermatEventMonitor;

    public void setEventManager(FermatEventMonitor fermatEventMonitor){
        this.fermatEventMonitor = fermatEventMonitor;

    }

    public void setIntraWalletUserManager(IntraWalletUserManager actorIntraUserManager){
        this.actorIntraUserManager = actorIntraUserManager;

    }

    public void setIntraUserManager( IntraUserManager intraUserNetworkServiceManager){
        this.intraUserNetworkServiceManager = intraUserNetworkServiceManager;

    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        if (((Service) this.actorIntraUserManager).getStatus() == ServiceStatus.STARTED){
            try
            {
                IntraUserActorConnectionCancelledEvent intraUserActorConnectionCancelledEvent = (IntraUserActorConnectionCancelledEvent) fermatEvent;
                this.actorIntraUserManager.disconnectIntraWalletUser(intraUserActorConnectionCancelledEvent.getIntraUserLoggedInPublicKey(),
                        intraUserActorConnectionCancelledEvent.getIntraUserToAddPublicKey());

                /**
                 * Confirm Disconnect on Network services
                 */

                intraUserNetworkServiceManager.confirmNotification(intraUserActorConnectionCancelledEvent.getIntraUserLoggedInPublicKey(), intraUserActorConnectionCancelledEvent.getIntraUserToAddPublicKey());
            }
            catch(CantDisconnectIntraWalletUserException e)
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
