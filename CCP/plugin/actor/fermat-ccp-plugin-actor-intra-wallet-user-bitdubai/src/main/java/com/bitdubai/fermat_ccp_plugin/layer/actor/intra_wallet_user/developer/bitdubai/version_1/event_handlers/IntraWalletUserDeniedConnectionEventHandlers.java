package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_ccp_api.layer.network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.exceptions.CantDenyConnectionException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.IntraWalletUserActorPluginRoot;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.IntraUserActorConnectionDeniedEvent;

import java.io.Serializable;

/**
 * Created by natalia on 17/08/15.
 */
public class IntraWalletUserDeniedConnectionEventHandlers implements FermatEventHandler,Serializable {
    /**
     * listener  INTRA_USER_CONNECTION_DENIED event
     * Change Actor status to DENIED
     */
    IntraWalletUserActorPluginRoot actorIntraUserManager;

    IntraUserManager intraUserNetworkServiceManager;

    FermatEventMonitor fermatEventMonitor;

    public void setEventManager(FermatEventMonitor fermatEventMonitor){
        this.fermatEventMonitor = fermatEventMonitor;

    }

    public void setActorIntraUserManager(IntraWalletUserActorPluginRoot actorIntraUserManager){
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
               IntraUserActorConnectionDeniedEvent intraUserActorConnectionDeniedEvent = (IntraUserActorConnectionDeniedEvent) fermatEvent;
                this.actorIntraUserManager.denyConnection(intraUserActorConnectionDeniedEvent.getIntraUserLoggedInPublicKey(),
                        intraUserActorConnectionDeniedEvent.getIntraUserToAddPublicKey());

                /**
                 * Confirm Denied on Network services
                 */
                intraUserNetworkServiceManager.confirmNotification(intraUserActorConnectionDeniedEvent.getIntraUserLoggedInPublicKey(), intraUserActorConnectionDeniedEvent.getIntraUserToAddPublicKey());

            }
            catch(CantDenyConnectionException e)
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
